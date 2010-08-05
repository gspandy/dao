package com.porpoise.dao.generator.model;

public enum ColType
{
    String, //
    Text
    {
        @Override
        public String getJavaName()
        {
            return "String";
        }
    }, //
    Boolean, //
    Integer, //
    Date, //
    BigDecimal, //
    Long;

    public String getJavaName()
    {
        return name();
    }//

}
