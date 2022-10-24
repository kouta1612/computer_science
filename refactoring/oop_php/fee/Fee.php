<?php

namespace fee;

interface Fee
{
    public function yen(): int;

    public function label(): string;
}
