const defaultProfileImage = "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/default_profile_image.jpg";
const profileImage = document.querySelector('#profile-image');
const originalImage = document.querySelector('#original-image');
profileImage.setAttribute("src", profileUrl);
if (profileUrl === null) {
    profileImage.setAttribute("src", defaultProfileImage);
    originalImage.setAttribute("value", defaultProfileImage);
}