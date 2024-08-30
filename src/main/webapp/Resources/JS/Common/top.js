document.addEventListener('DOMContentLoaded', function() {
	var searchContainer = document.querySelector('.search_container');
	var searchTxt = document.querySelector('.search_txt');
	var searchBtn = document.querySelector('.search_btn');
});
/*// 검색 버튼 클릭 시 하위 메뉴 숨기기
searchBtn.addEventListener('click', function(event) {
  event.stopPropagation(); // 상위 요소로의 이벤트 전파 방지
  searchContainer.querySelector('ul').style.display = 'none';
});

// 다른 곳을 클릭하면 하위 메뉴 숨기기
/*  document.addEventListener('click', function() {
  searchContainer.querySelector('ul').style.display = 'none';
});*/

// 프로필 관련 기능 
/* var profileImg = document.getElementById("profile-img");
 var profileInfo = document.querySelector(".profile-info");

 profileImg.addEventListener("click", function() {
   profileInfo.classList.toggle("show");
 });

 // 프로필 정보 영역 외의 다른 곳을 클릭하면 드롭다운이 닫히도록 설정
 document.addEventListener("click", function(event) {
   if (!profileInfo.contains(event.target) && !profileImg.contains(event.target)) {
	 profileInfo.classList.remove("show");
   }
 });
});
*/
// 레시피 리스트로 리다이렉트하는 함수
function redirectToRecipeList(category) {
	var encodedCategory = encodeURIComponent(category);
	window.location.href = "RecipeList?searchWord=" + encodedCategory;
};