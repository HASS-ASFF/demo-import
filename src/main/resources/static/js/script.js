/******************************************* START CUSTOM JS **************************************/
var tot = 0;
let totalSummary = '';


$(function () {
	// $('#leftnav-btn').click()
})
// DELETE
function deleteRow(rowId, prix) {
	tot = tot - prix;
	console.log("delete says: " + tot);


	$('#' + rowId).remove();

	//For purchase page
	if ($(".panier-drop").html() == "") {
		$(".panier-footer").html('');
		$(".badge-purchase").hide();
	}

}

function testConfirmDialog() {

	var result = confirm("Voulez vous continuer avec la suppression ?");

	if (result) {
		return true

	} else {
		alert("Suppression Annulé!");
		return false
	}
}

function testConfirmDialog2() {

	Swal.fire({
		title: 'Êtes-vous sûr?',
		text: "Voulez vous continuer  avec la suppression ?!",
		type: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Oui, je suis sûr!'
	}).then((result) => {
		if (result.value) {


			Swal.fire(
				'Succes!',
				'Merci Pour Confirmer.',
				'success'
			)

			return true
		}
		else
			return false;
	})
}


//show image on click

$(".img-product").on('click', function () {
	//declarer modal, le text,et le mnodal image oû l'image ouvre
	var modal = document.getElementById("myModal");
	var captionText = document.getElementById("caption");
	var modalImg = document.getElementById("img01");

	modal.style.display = "block";
	modalImg.src = "/images/products/" + $(this).attr("data-img");
	console.log(modalImg.src)
	captionText.innerHTML = $(this).attr("data-name");
	var span = document.getElementsByClassName("close")[0];

	//fermer l'image
	span.onclick = function () {
		modal.style.display = "none";
	}

});

/*************edit country */


$(document).on('click', ".edit-country", function () {

	document.getElementById("country-id").value = $(this).attr("data-id");
	document.getElementById("country-name").value = $(this).attr("data-name");


});


/* ADD Purchase//////////////////////////////////////////////////////*/

$(document).on('click', ".product-action", function () {

	let id = $(this).attr("prod-id");
	let name = $(this).attr("prod-name");
	let price = $(this).attr("prod-price");


	$(".badge-purchase").show();

	console.log(id);
	let newRowPanier = "<div class='dropdown-item row' id='" + id + "'> " +
		"<input type='hidden' name='id' value=" + id + " />" +
		"<a href='javascript:;' class='deletePur col'  onclick='event.stopPropagation();deleteRow(" + id + "," + price + ")'>X</a>" +
		"<a class='col'>" + name + "</a>" +
		"</div>";

	let footerPanier = "<div class='dropdown-divider'></div>" +
		"<div class='col-md-8'>" +
		"<button type='submit' name='action' class='btn ' value='detail'>Detail</button>" +
		"";

	if ($(".panier-drop").html() == "") {
		$(".panier-drop").html($(".panier-drop").html() + newRowPanier);
		$(".panier-footer").html($(".panier-footer").html() + footerPanier);
		console.log("if empty")
	}
	else {
		$(".panier-drop").html($(".panier-drop").html() + newRowPanier);
		console.log("if not empty")
	}

	console.log(2)






});




/******************************************* END CUSTOM JS **************************************/

