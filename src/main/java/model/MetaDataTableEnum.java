package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MetaDataTableEnum {
    private String nameTable;
    private String keyNameEnum;
    private List<ValueEnumFromColumnName> valuesEnumFromColumnName;

    public MetaDataTableEnum(){}
}
