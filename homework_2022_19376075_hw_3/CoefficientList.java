import java.math.BigInteger;

public class CoefficientList {
    @Override

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoefficientList)) {
            return false;
        }
        CoefficientList that = (CoefficientList) o;
        if (this.getNumber().getNumber().equals(that.getNumber().getNumber())) {
            if (this.getSin() != null && that.getSin() != null) {
                if (this.getSin().getValues().equals(that.getSin().getValues())) {
                    if (this.getCos() != null && that.getCos() != null) {
                        if (this.getCos().getValues().equals(that.getCos().getValues())) {
                            //System.out.println("qwq1");
                            return true;
                        }
                    } else if (this.getCos() == null && that.getCos() == null) {
                        //System.out.println("qwq2");
                        return true;
                    }
                }
            } else if (this.getSin() == null && that.getSin() == null) {
                if (this.getCos() != null && that.getCos() != null) {
                    if (this.getCos().getValues().equals(that.getCos().getValues())) {
                        //System.out.println("qwq3");
                        return true;
                    }
                } else if (this.getCos() == null && that.getCos() == null) {
                    //System.out.println("qwq4");
                    return true;
                }
            }
        }
        return false;
    }

    private ValueList[] coefficients;

    public ValueList[] getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(ValueList[] coefficients) {
        this.coefficients = coefficients;
    }

    public CoefficientList(ValueList[] coefficients) {
        this.coefficients = coefficients;
    }

    public ValueList getNumber() {
        return this.coefficients[0];
    }

    public ValueList getSin() {
        if (coefficients[1] != null) {
            return this.coefficients[1];
        } else {
            return null;
        }
    }

    public ValueList getCos() {
        if (coefficients[2] != null) {
            return this.coefficients[2];
        } else {
            return null;
        }
    }

    public int isNotExpression()
    {
        if (getSin() == null && getCos() == null &&
                getNumber().getNumber().equals(BigInteger.ZERO))
        {
            //任意数均可
            return 1;
        }
        else if (getSin() != null && getCos() == null &&
                getNumber().getNumber().equals(BigInteger.ZERO))
        {
            //仅1可以
            return 3;
        }
        else if (getSin() == null && getCos() != null &&
                getNumber().getNumber().equals(BigInteger.ZERO))
        {
            return 3;
        }
        else if (getSin() == null && getCos() == null &&
                !getNumber().getNumber().equals(BigInteger.ZERO))
        {
            return 2;
        }
        else
        {
            //均不可
            return 3;
        }
    }

}
