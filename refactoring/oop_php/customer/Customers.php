<?php

namespace customer;

require_once 'Customer.php';

class Customers
{
    private array $customers;

    public function __construct(Customer ...$customers)
    {
        $this->customers = $customers;
    }

    public static function construct(Customer ...$customers)
    {
        return new self(...$customers);
    }

    public function add(Customer $customer)
    {
        return new self(...array_merge($this->customers, [$customer]));
    }

    public function getCustomers()
    {
        return $this->customers;
    }
}
