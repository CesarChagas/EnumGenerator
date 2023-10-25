package model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Row {

    private String key;

    private String value;

    private List<ValueEnumFromColumnName> values;
}
