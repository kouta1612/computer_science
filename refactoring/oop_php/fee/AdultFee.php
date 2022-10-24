<?php

namespace fee;

require_once 'Fee.php';

class AdultFee implements Fee
{
    public function yen(): int
    {
        return 100;
    }

    public function label(): string
    {
        return "大人";
    }
}
