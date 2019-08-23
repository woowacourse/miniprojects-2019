package techcourse.fakebook.service.dto;

public class UserResponse {
    private String email;
    private String name;
    private String gender;
    private String coverUrl;
    private String birth;
    private String introduction;

    public UserResponse(String email, String name, String gender, String coverUrl, String birth, String introduction) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.coverUrl = coverUrl;
        this.birth = birth;
        this.introduction = introduction;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getBirth() {
        return birth;
    }

    public String getIntroduction() {
        return introduction;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", birth='" + birth + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
