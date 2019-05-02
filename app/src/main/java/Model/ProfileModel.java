package Model;

public class ProfileModel {

    private int id;
    private String fName, lName, imageUrl;

    public ProfileModel() {
    }

    public ProfileModel(int id, String fName, String lName, String imageUrl) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
