package demo.dada.com.apnacomplexexercise.data;

public class MainData {

    private int image;
    private String name;

    public MainData(int image, String name)
    {
        this.image = image;
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
