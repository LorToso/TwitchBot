package irc.keywords;

public class Keyword {
    public static final String keywordPrefix = "!";

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

        this.keyword = keywordPrefix + keyword;
        this.parameters = parameters;
    }


    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
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
