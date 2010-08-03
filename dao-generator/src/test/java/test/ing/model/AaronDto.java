

package test.ing.model;

import java.math.BigDecimal;
import java.util.List;

import test.ing.model.AaronDto;

/**
 * AaronDto
 */
public final class AaronDto
{
    /**
     * id
     */
    private final Long id;
    
    /**
     * name
     */
    private final String name;
    

    public AaronDto(
final Long id, final String name    
    )
    {
        super();
        this.id = id; 
        this.name = name; 
    }

    /**
     * id
     */
    public Long getID()
    {
        return this.id;
    }
    
    /**
     * name
     */
    public String getName()
    {
        return this.name;
    }
    

}
