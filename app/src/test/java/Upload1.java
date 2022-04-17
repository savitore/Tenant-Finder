public class Upload1
{
    private String name,url;

    public Upload1()
    {

    }
    public Upload1(String name, String url)
    {
        if(name.trim().equals(""))
        {
            name="No name";
        }
        this.name=name;
        this.url=url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
