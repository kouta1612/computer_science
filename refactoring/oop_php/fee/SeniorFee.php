<?php

namespace fee;

require_once 'Fee.php';

class SeniorFee implements Fee
{
    public function yen(): int
    {
        return 80;
    }

    public function label(): string
    {
        return "シニア";
    }
}
