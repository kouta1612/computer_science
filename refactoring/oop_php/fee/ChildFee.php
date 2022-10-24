<?php

namespace fee;

require_once 'Fee.php';

class ChildFee implements Fee
{
    public function yen(): int
    {
        return 50;
    }

    public function label(): string
    {
        return "子供";
    }
}
