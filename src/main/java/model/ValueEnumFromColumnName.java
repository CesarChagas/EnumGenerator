package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ValueEnumFromColumnName{

    private String valueFromColumnName;
    private Integer type; //  0 for String or 1 for Long
}
