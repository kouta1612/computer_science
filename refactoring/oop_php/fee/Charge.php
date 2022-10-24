<?php

namespace fee;

require_once 'Fee.php';

class Charge
{
    public Fee $fee;

    public function __construct(Fee $fee)
    {
        $this->fee = $fee;
    }

    public function yen(): int
    {
        return $this->fee->yen();
    }
}
