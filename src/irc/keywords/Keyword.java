package irc.keywords;

import java.util.Arrays;

public class Keyword {
    public static final String keywordPrefix = "!";
    private static Class[] validParameterClasses = new Class[]{String.class, Integer.class, Float.class};

    private Class[] parameters;
    private String keyword;

    public Keyword(String keyword) {
        this(keyword, new Class[0]);
    }
    public Keyword(String keyword, Class ...parameters) {

        if(keyword.contains(" "))
            throw new IllegalArgumentException("Space in keyword is invalid.");
        if(keyword.length()==0)
            throw new IllegalArgumentException("Keyword is invalid.");
        if(keyword.contains("!"))
            throw new IllegalArgumentException("Illegal symbol in keyword.");

        if(!areValidParameterClasses(parameters))
            throw new IllegalArgumentException("The parameter-list is invalid.");

        this.keyword = keywordPrefix + keyword;
        this.parameters = parameters;
    }

    private boolean areValidParameterClasses(Class[] parametersToCheck) {
        for(Class parameter : parametersToCheck)
        {
            if(!Arrays.asList(validParameterClasses).contains(parameter))
                return false;
        }
        return true;
    }


    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;


        if(!(o instanceof Keyword))
            return false;

        Keyword keyword1 = (Keyword) o;

        return keyword.equals(keyword1.keyword);
    }

    @Override
    public String toString() {
        return getKeyString();
    }

    public Class[] getParameterTypes() {
        return parameters;
    }
    public String getKeyString()
    {
        return keyword;
    }
}
