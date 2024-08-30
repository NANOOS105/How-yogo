<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요고, 어때?</title>
<link rel="stylesheet" href="/Resources/CSS/Board/recipeWrite.css">
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
</head>
<body>
<head>
<jsp:include page="/WEB-INF/views/common/top.jsp" />
</head>

<h2>레시피 작성</h2>
<form action="RecipeWrite" method="post" enctype="multipart/form-data"
	id="form">
	<table>
		<tr>
			<th>제목</th>
			<td><input type="text" name="recipeTitle" required id="myinput"></td>
		</tr>
		<tr>
			<th>글쓴이</th>
			<td><input type="text" name="nickname" value="${bean.nickname}"
				id="myinput" readonly> <input type="hidden" name="memberid"
				value="${bean.memberid}"></td>
		</tr>
		<tr>
			<th>재료</th>
			<td><div id="ingredientContainer">
					<div id="ingredient-button">
						<input type="text" name="ingredientName1" placeholder="재료"
							required> <input type="text" name="ingredientAmount1"
							placeholder="분량" required>
					</div>
				</div>
				<button type="button" onclick="addIngredient()">재료 추가</button></td>
		</tr>
		<tr>
			<th>대표 이미지</th>
			<td>
				<!-- 파일 선택 시 이미지 미리 보기 --> <input type="file" name="mainOName"
				onchange="previewImage(event, 'preview_m')" required id="myinput" />
				<!-- 이미지 미리 보기를 위한 영역 --> <img id="preview_m"
				src="/uploads/${ dto.mainOName }" alt="이미지 미리보기"
				style="max-width: 100px; max-height: 100px; display: none;">
			</td>
		</tr>
		<tr>
			<th>재료 이미지</th>
			<td>
				<!-- 파일 선택 시 이미지 미리 보기 --> <input type="file" name="inOName"
				onchange="previewImage(event, 'preview_i')" required id="myinput" />
				<!-- 이미지 미리 보기를 위한 영역 --> <img id="preview_i"
				src="/uploads/${ dto.inOName }" alt="이미지 미리보기"
				style="max-width: 100px; max-height: 100px; display: none;">
			</td>
		</tr>
		<tr>
			<th>카테고리</th>
			<td><select name="recipeCategory" required>
					<option value="한식">한식</option>
					<option value="양식">양식</option>
					<option value="일식">일식</option>
					<option value="중식">중식</option>
					<option value="기타">기타</option>
			</select></td>
		</tr>
	</table>

	<h3>요리 순서</h3>
	<div id="stepContainer">
		<div>
			<h4>STEP 1</h4>
			<textarea name="stepContent1" required></textarea>
			<!-- 파일 선택 시 이미지 미리 보기 -->
			<input type="file" name="imgOName1"
				onchange="previewImage(event, 'preview_1')" required id="myinput" />
			<!-- 이미지 미리 보기를 위한 영역 -->
			<img id="preview_1" src="/uploads/${ dto.inOName1 }" alt="이미지 미리보기"
				style="max-width: 100px; max-height: 100px; display: none;">
		</div>
	</div>
	<button type="button" onclick="addStep()">순서 추가</button>

	<input type="submit" value="작성 완료" id="myinput">
</form>
<script>
let stepCount = 1;
let ingredientCount = 1;

function addIngredient() {
    ingredientCount++;
    let ingredientDiv = document.createElement("div");
    ingredientDiv.innerHTML = `
        <input type="text" name="ingredientName\${ingredientCount}" placeholder="재료" required>
        <input type="text" name="ingredientAmount\${ingredientCount}" placeholder="분량" required>
        <button type="button" class="delete-ingredient" onclick="deleteIngredient(this)">삭제</button>
    `;
    document.getElementById("ingredientContainer").appendChild(ingredientDiv);
}

function deleteIngredient(btn) {
    let ingredientDiv = btn.parentNode;
    ingredientDiv.parentNode.removeChild(ingredientDiv);
    
    // 삭제 후 재료 번호 재정렬
    let ingredientDivs = document.querySelectorAll("#ingredientContainer > div");
    for (let i = 0; i < ingredientDivs.length; i++) {
        ingredientDivs[i].querySelector("input[name^='ingredientName']").name = `ingredientName\${i + 1}`;
        ingredientDivs[i].querySelector("input[name^='ingredientAmount']").name = `ingredientAmount\${i + 1}`;
    }
    
    ingredientCount = ingredientDivs.length;
}

function addStep() {
    stepCount++;
    let stepDiv = document.createElement("div");
    stepDiv.innerHTML = `
        <h4>STEP \${stepCount}</h4>
        <textarea name="stepContent\${stepCount}" required></textarea>
		<input type="file" id="myinput" name="imgOName\${stepCount}" onchange="previewImage(event, 'preview_\${stepCount}')" required/>
		<img id="preview_\${stepCount}" class="preview-image" src="/uploads/${ dto.inOName }\${stepCount} " alt="이미지 미리보기" style="display: none;">
		<button type="button" class="delete-step" onclick="deleteStep(this)">삭제</button>
    `;
    document.getElementById("stepContainer").appendChild(stepDiv);
}

function deleteStep(btn) {
    let stepDiv = btn.parentNode;
    stepDiv.parentNode.removeChild(stepDiv);
    
    // 삭제 후 STEP 번호 재정렬
    let stepDivs = document.querySelectorAll("#stepContainer > div");
    for (let i = 0; i < stepDivs.length; i++) {
        stepDivs[i].querySelector("h4").textContent = `STEP \${i + 1}`;
        stepDivs[i].querySelector("textarea").name = `stepContent\${i + 1}`;
        stepDivs[i].querySelector("input[type='file']").name = `imgOName\${i + 1}`;
        stepDivs[i].querySelector("img").id = `preview_\${i + 1}`;
    }
    
    stepCount = stepDivs.length;
}

function previewImage(event, previewId) {
    var reader = new FileReader();
    reader.onload = function(){
        var output = document.getElementById(previewId);
        output.style.display = 'block';
        output.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
}
</script>
</body>
</html>