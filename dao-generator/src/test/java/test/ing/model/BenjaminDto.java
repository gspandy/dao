

package test.ing.model;

import java.math.BigDecimal;
import java.util.List;

import test.ing.model.BenjaminDto;

/**
 * BenjaminDto
 */
public final class BenjaminDto
{
    /**
     * id
     */
    private final Long id;
    
    /**
     * aaronId
     */
    private final Long aaronId;
    
    /**
     * name
     */
    private final String name;
    

    public BenjaminDto(
final Long id, final Long aaronId, final String name    
    )
    {
        super();
        this.id = id; 
        this.aaronId = aaronId; 
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
     * aaronId
     */
    public Long getAARON_ID()
    {
        return this.aaronId;
    }
    
    /**
     * name
     */
    public String getName()
    {
        return this.name;
    }
    

}
