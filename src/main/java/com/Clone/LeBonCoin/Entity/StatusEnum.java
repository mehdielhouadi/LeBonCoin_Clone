package com.Clone.LeBonCoin.Entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum StatusEnum {
    AVAILABLE, SOLD_OUT;
}
