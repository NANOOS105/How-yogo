<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<link rel="stylesheet" href="/Resources/CSS/Board/recipeWrite.css">
<title>요고 어때?</title>
</head>
<body>
<head>
<jsp:include page="/WEB-INF/views/common/top.jsp" />
</head>

<h2>레시피 수정</h2>
<form action="RecipeEditProcess" method="post"
	enctype="multipart/form-data">
	<input type="hidden" name="recipeId" value="${boardDto.recipeId}">
	<input type="hidden" name="memberid" value="${boardDto.memberid}">
	<table>
		<tr>
			<th>제목</th>
			<td><input type="text" id="myinput" name="recipeTitle"
				value="${boardDto.recipeTitle}" required></td>
		</tr>
		<tr>
			<th>재료</th>
			<td>
				<div id="ingredientContainer">
					<c:forEach var="ingredient" items="${ingredientsMap}"
						varStatus="status">
						<div id="ingredient-button">
							<input type="text" name="ingredientName${status.index + 1}"
								value="${ingredient.key}" required> <input type="text"
								name="ingredientAmount${status.index + 1}"
								value="${ingredient.value}" required>
							<button type="button" class="delete-ingredient"
								onclick="deleteIngredient(this)">삭제</button>
						</div>
					</c:forEach>
				</div>
				<button type="button" onclick="addIngredient()">재료 추가</button>
			</td>
		</tr>
		<tr>
			<th>대표 이미지</th>
			<td><img src="/uploads/${boardDto.mainSName}" alt="대표 이미지">
				<input type="file" name="mainOName" id="myinput"> <input
				type="hidden" name="existingMainName" value="${boardDto.mainOName}"
				id="myinput"> <input type="hidden" name="existingMainImage"
				value="${boardDto.mainSName}" id="myinput"></td>
		</tr>
		<tr>
			<th>재료 이미지</th>
			<td><img src="/uploads/${boardDto.inSName}" alt="재료 이미지">
				<input type="file" name="inOName" id="myinput"> <input
				type="hidden" name="existingInName" value="${boardDto.inOName}"
				id="myinput"> <input type="hidden" name="existingInImage"
				value="${boardDto.inSName}" id="myinput"></td>
		</tr>
		<tr>
			<th>카테고리</th>
			<td><select name="recipeCategory" required>
					<option value="한식"
						${boardDto.recipeCategory == '한식' ? 'selected' : ''}>한식</option>
					<option value="양식"
						${boardDto.recipeCategory == '양식' ? 'selected' : ''}>양식</option>
					<option value="일식"
						${boardDto.recipeCategory == '일식' ? 'selected' : ''}>일식</option>
					<option value="중식"
						${boardDto.recipeCategory == '중식' ? 'selected' : ''}>중식</option>
					<option value="기타"
						${boardDto.recipeCategory == '기타' ? 'selected' : ''}>기타</option>
			</select></td>
		</tr>
	</table>

	<h3>요리 순서</h3>
	<div id="stepContainer">
		<input type="hidden" name="stepCount" value="${stepList.size()}">
		<c:forEach var="step" items="${stepList}" varStatus="status">
			<div>
				<h4>STEP ${status.count}</h4>
				<textarea name="stepContent${status.count}" required>${step.stepContent}</textarea>
				<img src="/uploads/${step.imgSName}" alt="요리 순서 이미지"> <input
					type="file" id="myinput" name="imgOName${status.count}"
					onchange="previewImage(event, 'preview_${status.count}')" /> <img
					id="preview_${status.count}" class="preview-image"
					src="/uploads/${ dto.inOName }${status.count} " alt="이미지 미리보기"
					style="display: none;"> <input type="hidden" id="myinput"
					name="existingStepName${status.count}" value="${step.imgOName}">
				<input type="hidden" id="myinput"
					name="existingStepImage${status.count}" value="${step.imgSName}">
				<button type="button" class="delete-step" onclick="deleteStep(this)">삭제</button>
			</div>
		</c:forEach>
	</div>
	<button type="button" onclick="addStep()">순서 추가</button>

	<input type="submit" value="수정 완료" id="myinput">
</form>

<script>
        let stepCount = ${stepList.size()};
        let ingredientCount = ${ingredientsMap.size()};

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
            document.getElementsByName("stepCount")[0].value = stepCount;
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