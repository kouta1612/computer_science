<?php

namespace fee;

use fee\Fee;

require_once 'Fee.php';

enum FeeType implements Fee
{
    case Adult;
    case Child;
    case Senior;

    public function yen(): int
    {
        return match ($this) {
            FeeType::Adult => 100,
            FeeType::Child => 50,
            FeeType::Senior => 80
        };
    }

    public function label(): string
    {
        return match ($this) {
            FeeType::Adult => "大人",
            FeeType::Child => "子供",
            FeeType::Senior => "シニア"
        };
    }
}
