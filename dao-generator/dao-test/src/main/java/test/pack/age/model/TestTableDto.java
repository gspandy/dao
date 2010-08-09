

package test.pack.age.model;

import java.math.BigDecimal;
import java.util.List;

import test.pack.age.model.TestTableDto;

/**
 * TestTableDto
 */
public final class TestTableDto
{
    /**
     * id
     */
    private final Long id;
    
    /**
     * lastname
     */
    private final String lastname;
    
    /**
     * firstname
     */
    private final String firstname;
    

    public TestTableDto(
final Long id, final String lastname, final String firstname    
    )
    {
        super();
        this.id = id; 
        this.lastname = lastname; 
        this.firstname = firstname; 
    }

    /**
     * id
     */
    public Long getId()
    {
        return this.id;
    }    
    /**
     * lastname
     */
    public String getLastName()
    {
        return this.lastname;
    }    
    /**
     * firstname
     */
    public String getFirstName()
    {
        return this.firstname;
    }    

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "TestTableDto";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLastName() == null) ? 0 : getLastName().hashCode());
        result = prime * result + ((getFirstName() == null) ? 0 : getFirstName().hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestTableDto other = (TestTableDto) obj;


        if (getId() == null)
        {
            if (other.getId() != null)
            {
                return false;
            }
        }
        else if (!getId().equals(other.getId()))
        {
            return false;
        }

        if (getLastName() == null)
        {
            if (other.getLastName() != null)
            {
                return false;
            }
        }
        else if (!getLastName().equals(other.getLastName()))
        {
            return false;
        }

        if (getFirstName() == null)
        {
            if (other.getFirstName() != null)
            {
                return false;
            }
        }
        else if (!getFirstName().equals(other.getFirstName()))
        {
            return false;
        }
        return true;
    }
}
