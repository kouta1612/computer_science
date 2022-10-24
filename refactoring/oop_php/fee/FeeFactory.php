<?php

namespace fee;

require_once 'fee/Fee.php';
require_once 'fee/AdultFee.php';
require_once 'fee/ChildFee.php';
require_once 'fee/SeniorFee.php';

class FeeFactory
{
    private array $types;

    private function __construct()
    {
        $this->types = [
            'adult' => new AdultFee,
            'child' => new ChildFee,
            'senior' => new SeniorFee,
        ];
    }

    public static function feeByName(string $name): Fee
    {
        $factory = new static();
        return $factory->types[$name];
    }
}
