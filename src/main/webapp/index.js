function kmValue(){

}
function sendData(){
	let sendpos = document.sendpos;
	let lat = sendpos.lat;
	let lnt = sendpos.lnt;
	
	if(lat.value == ""){
		alert("lat를 입력하세요");
		lat.focus();
		return false;
	}
	if(lnt.value == ""){
		alert("lnt를 입력하세요");
		lnt.focus();
		return false;
	}
	sendpos.submit();
}
