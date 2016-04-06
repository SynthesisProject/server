package coza.opencollab.synthesis.util;

import java.io.Serializable;
import java.util.Date;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author OpenCollab
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimplePojo implements Serializable{
    private String valueString;
    private int valueInt;
    private Date valueDate;
    private boolean valueBool;
    
    public SimplePojo(){}

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public int getValueInt() {
        return valueInt;
    }

    public void setValueInt(int valueInt) {
        this.valueInt = valueInt;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public boolean isValueBool() {
        return valueBool;
    }

    public void setValueBool(boolean valueBool) {
        this.valueBool = valueBool;
    }

    @JsonIgnore
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @JsonIgnore
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (getClass().isInstance(obj)) {
            SimplePojo other = (SimplePojo) obj;
            return (valueString == null ? other.valueString == null : valueString.equals(other.valueString))
                    && (valueInt == other.valueInt)
                    && (valueDate == null ? other.valueDate == null : valueDate.equals(other.valueDate))
                    && (valueBool == other.valueBool);
        } else {
            return false;
        }
    }

    @JsonIgnore
    @Override
    public String toString() {
        return getClass().getName()
                + "[valueString=" + valueString
                + ";valueInt=" + valueInt
                + ";valueDate=" + valueDate
                + ";valueBool=" + valueBool
                + "]";
    }
}
