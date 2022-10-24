<?php

namespace shopping;

class ShippingCost
{
    const MINIMUM_POR_FREE = 3000;
    const COST = 500;

    private $basePrice;

    function __construct(int $basePrice)
    {
        $this->basePrice = $basePrice;
    }

    function amount(): int
    {
        if ($this->basePrice < self::MINIMUM_POR_FREE) return self::COST;

        return 0;
    }
}
