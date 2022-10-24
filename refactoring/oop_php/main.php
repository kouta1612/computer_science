<?php

namespace main;

require_once 'shopping/ShippingCost.php';
require_once 'fee/Charge.php';
require_once 'fee/FeeFactory.php';
require_once 'fee/FeeType.php';
require_once 'customer/Customer.php';
require_once 'customer/Customers.php';

use customer\Customer;
use customer\Customers;
use fee\Charge;
use fee\FeeFactory;
use fee\FeeType;
use shopping\ShippingCost;

shopping();
fee();
customers();

function customers()
{
    $customerA = new Customer("hagiwara");
    $customerB = new Customer("ogawa");
    $customerC = new Customer("suyama");

    // 3通りの構文あり
    $customers = Customers::construct($customerA, $customerB);
    $customers = Customers::construct(...[$customerA, $customerB]);
    $customers = new Customers($customerA, $customerB);

    $addedCustomers = $customers->add($customerC);
    foreach ($addedCustomers->getCustomers() as $customer) {
        echo $customer->getName() . PHP_EOL;
    }
}

function shopping()
{
    $basePrice = 2000;

    $shippingCost = new ShippingCost($basePrice);

    $itemPrice = ($basePrice + $shippingCost->amount());

    echo $itemPrice . PHP_EOL;
}

function fee()
{
    // ファクトリパターン
    $adultFee = FeeFactory::feeByName('adult');
    $charge = new Charge($adultFee);
    echo $charge->yen() . PHP_EOL;

    // Enumパターン
    echo FeeType::Adult->yen() . PHP_EOL;
}
