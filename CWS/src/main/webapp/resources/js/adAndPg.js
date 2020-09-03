$(document)
		.ready(
				function() {

					if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|BB|PlayBook|IEMobile|Windows Phone|Kindle|Silk|Opera Mini/i
							.test(navigator.userAgent)) {
						document.getElementById('openAppDiv').style.display = "";
						document.getElementById('showButtonModal').style.display = "none";
					} else {
						$("#showButtonModal").modal('show');
					}

				});

function buyPack() {
	document.form1.submit();
}

function extraPack() {
	document.staticGrantValue.submit();
}

function showAd(duration) {
	callback(duration); // duration
	$("#staticModal").modal('show');
}

function callback(duration) {
	var seconds_left = duration;
	var totalDuration = duration * 1000;
	var interval = setInterval(function() {
		$('#timer_div').html(--seconds_left + " Sec Left");
		// <![CDATA[
		if (seconds_left <= 2) {
			$('#timer_div').html("You are Ready to Access free min!");
			clearInterval(interval);
		}
	}, 1000);

	setTimeout(function() {
		$('#staticModal').modal('hide');
		$("#showButtonModal").modal('hide');
		$("#staticAdDiv").modal('show');
	}, totalDuration);
}
// ]]>

function showRate(adId, staticRateCount, staticRatePoint) {

	$('#staticAdId').val(adId);
	$('#staticRateCount').val(staticRateCount);
	$('#staticRatePoint').val(staticRatePoint);
	$("#staticAdRatingDiv").modal('show');
}

// <![CDATA[
function sendRate(val) {
	var adId = $('#staticAdId').val();
	var rateCount = $('#staticRateCount').val();
	var totalRatePoint = $('#staticRatePoint').val();
	
	totalRatePoint = parseInt(totalRatePoint) + parseInt(val);
	rateCount = parseInt(rateCount);
	rateCount += 1;
	
	var ratePointval = document.getElementById("ratePoint");
	document.getElementById('ratePoint').value = ratePointval.value+""+adId+"|"+val+",";
	var avgVal = totalRatePoint / rateCount;
	if (avgVal != 0)
		avgVal = Math.round(avgVal * 10) / 10;

	document.getElementById(''+adId+'totalrateVal').value = avgVal + " average";
	$('#staticAdRatingDiv').modal('hide')
	$.ajax({
		url : "./rateCount?adId=" + adId + "&rateCount=" + rateCount
				+ "&totalRatePoint=" + totalRatePoint,
		crossDomain : true,
		type : "POST",
		success : function(data) {
			document.getElementById(''+adId+'rateVal').value = rateCount + " ratings";
			document.getElementById(''+adId+'rate').onclick = null;
		}
	});
}
// ]]>

// <![CDATA[
function staticLike(adId, likeCount) {
	likeCount = parseInt(likeCount);
	likeCount += 1;
	var opinionval = document.getElementById("opinion");
	document.staticGrantValue.opinion.value = opinionval.value+""+adId+"|like,";
	$.ajax({
		url : "./rate?adId=" + adId + "&likeCount=" + likeCount,
		crossDomain : true,
		type : "POST",
		success : function(data) {
			document.getElementById(''+adId+'staticlike').innerHTML = data;
			document.getElementById(''+adId+'likeoff').onclick = null;
			document.getElementById(''+adId+'dislikeoff').onclick = null;

		}
	});
}

function redirectStaticLike(adId, likeCount, redirectionUrl) {
	likeCount = parseInt(likeCount);
	likeCount += 1;
	var opinionval = document.getElementById("opinion");
	document.staticGrantValue.opinion.value = opinionval.value+""+adId+"|like,";
	$.ajax({
		url : "./rate?adId=" + adId + "&likeCount=" + likeCount,
		crossDomain : true,
		type : "POST",
		success : function(data) {
			document.staticGrantValue.submit();
			window.open(redirectionUrl);
		}
	});
}

function redirectStaticDislike(adId, dislikeCount) {
	dislikeCount = parseInt(dislikeCount);
	dislikeCount += 1;
	var opinionval = document.getElementById("opinion");
	document.staticGrantValue.opinion.value = opinionval.value+""+adId+"|dislike,";
	$.ajax({
		url : "./rate?adId=" + adId + "&disLikeCount=" + dislikeCount,
		crossDomain : true,
		type : "POST",
		success : function(data) {
			document.staticGrantValue.submit();
		}
	});
}


function staticDislike(adId, dislikeCount) {
	dislikeCount = parseInt(dislikeCount);
	dislikeCount += 1;
	var opinionval = document.getElementById("opinion");
	document.staticGrantValue.opinion.value = opinionval.value+""+adId+"|dislike,";
	$.ajax({
		url : "./rate?adId=" + adId + "&disLikeCount=" + dislikeCount,
		crossDomain : true,
		type : "POST",
		success : function(data) {
			document.getElementById(''+adId+'staticdislike').innerHTML = data;
			document.getElementById(''+adId+'likeoff').onclick = null;
			document.getElementById(''+adId+'dislikeoff').onclick = null;
		}
	});
}

// ]]>

// ************************************* Video Ad
// ****************************************************************

function showVideoAd() {
	$("#videoModal").modal('show');
}

$(document).ready(function() {
	$("#myVideo").bind("click", function() {
		var vid = $(this).get(0);
		var vide = document.getElementById("myVideo");
		var duration = vide.duration;
		if (vid.paused) {
			vid.play();
			videoCallback(duration);// duration or 3
		}
	});
});

function videoCallback(duration) {
	var totalDuration = duration * 1000;
	var seconds_left = duration;
	var interval = setInterval(
			function() {
				document.getElementById('timer_div').innerHTML = --seconds_left
						+ " Sec Left";
				// <![CDATA[
				if (seconds_left <= 2) {
					document.getElementById('timer_div').innerHTML = "You are Ready to Access free min!";
					clearInterval(interval);
				}
			}, 1000);
	setTimeout(function() {
		$('#videoModal').modal('hide')
		$("#showButtonModal").modal('hide');
		$("#videoAdDiv").modal('show');
	}, totalDuration);
}
// ]]>

function videoExtraPack() {
	document.videoGrantValue.submit();
}

function showVideoRate() {
	$("#videoAdRatingDiv").modal('show');
}

// <![CDATA[
function sendVideoRate(adId, rateVal, totalRatePoint, val) {

	totalRatePoint = parseInt(totalRatePoint) + parseInt(val);
	rateVal = parseInt(rateVal);
	rateVal += 1;
	
	var ratePointval = document.getElementById("ratePoint");
	document.videoGrantValue.ratePoint.value = ratePointval.value+""+adId+"|"+val+",";
	var avgVal = totalRatePoint / rateVal;
	if (avgVal != 0)
		avgVal = Math.round(avgVal * 10) / 10;

	
	document.getElementById('videorateVal').value = avgVal + " average";

	$('#videoAdRatingDiv').modal('hide')
	$.ajax({
		url : "./rateCount?adId=" + adId + "&rateCount=" + rateVal
				+ "&totalRatePoint=" + totalRatePoint,
		crossDomain : true,
		type : "POST",
		success : function(data) {
			document.getElementById('videoratePoint').value = rateVal
					+ " ratings";
			document.getElementById("vidRate").onclick = null;
		}
	});
}
// ]]>

// <![CDATA[
function videoLike(adId, likeCount) {
	likeCount = parseInt(likeCount);
	likeCount += 1;
	
	var opinionval = document.getElementById("opinion");
	document.videoGrantValue.opinion.value = opinionval.value+""+adId+"|like,";
	$.ajax({
		url : "./rate?adId=" + adId + "&likeCount=" + likeCount,
		type : "POST",
		crossDomain : true,
		success : function(data) {
			document.getElementById('videolike').innerHTML = data;
			document.getElementById("videoimglike").onclick = null;
			document.getElementById("videoimgdislike").onclick = null;
		}
	});
}

function videoDislike(adId, dislikeCount) {
	dislikeCount = parseInt(dislikeCount);
	dislikeCount += 1;
	
	var opinionval = document.getElementById("opinion");
	document.videoGrantValue.opinion.value = opinionval.value+""+adId+"|dislike,";
	$.ajax({
		url : "./rate?adId=" + adId + "&disLikeCount=" + dislikeCount,
		type : "POST",
		crossDomain : true,
		success : function(data) {
			document.getElementById('videodislike').innerHTML = data;
			document.getElementById("videoimglike").onclick = null;
			document.getElementById("videoimgdislike").onclick = null;
		}
	});
}

// ]]>

// ************************************* Audio Ad
// ****************************************************************

function showAudioAd() {
	$("#audioModal").modal('show');
}

$(document)
		.ready(
				function() {

					var yourAudio = document.getElementById('myAudio'), ctrl = document
							.getElementById('audioControl');
					var vide = document.getElementById("myAudio");

					vide.onloadedmetadata = function() {
						var duration = vide.duration;
						audiocallback(duration);// duration or 10
					};

					ctrl.onclick = function() {

						// Update the Button
						var pause = ctrl.innerHTML === 'pause!';
						ctrl.innerHTML = pause ? 'play!'
								: '<input type="button" class="btn btn-default" value="Play Audio" />';
						// Update the Audio
						var method = pause ? 'play' : 'play';
						myAudio[method]();

						// Prevent Default Action
						return false;
					};

				});

function audiocallback(duration) {
	var totalDuration = duration * 1000;
	var seconds_left = duration;
	var interval = setInterval(
			function() {
				document.getElementById('timer_div').innerHTML = --seconds_left
						+ " Sec Left";
				// <![CDATA[
				if (seconds_left <= 2) {
					document.getElementById('timer_div').innerHTML = "You are Ready to Access free min!";
					clearInterval(interval);
				}
			}, 1000);
	setTimeout(function() {
		$('#audioModal').modal('hide')
		$("#showButtonModal").modal('hide');
		$("#audioAdDiv").modal('show');
		// document.form.submit();
	}, totalDuration); // totalDuration
}
// ]]>

function audioExtraPack() {
	document.audioGrantValue.submit();
}

function showAudioRate() {
	$("#audioAdRatingDiv").modal('show');
}

// <![CDATA[
function audioLike(adId, likeCount) {
	likeCount = parseInt(likeCount);
	likeCount += 1;
	var opinionval = document.getElementById("opinion");
	document.audioGrantValue.opinion.value = opinionval.value+""+adId+"|like,";
	$.ajax({
		url : "./rate?adId=" + adId + "&likeCount=" + likeCount,
		crossDomain : true,
		type : "POST",
		success : function(data) {
			document.getElementById('audiolike').innerHTML = data;
			document.getElementById("audioimglike").onclick = null;
			document.getElementById("audioimgdislike").onclick = null;
		}
	});
}

function audioDislike(adId, dislikeCount) {
	dislikeCount = parseInt(dislikeCount);
	dislikeCount += 1;
	var opinionval = document.getElementById("opinion");
	document.audioGrantValue.opinion.value = opinionval.value+""+adId+"|dislike,";
	$.ajax({
		url : "./rate?adId=" + adId + "&disLikeCount=" + dislikeCount,
		crossDomain : true,
		type : "POST",
		success : function(data) {
			document.getElementById('audiodislike').innerHTML = data;
			document.getElementById("audioimglike").onclick = null;
			document.getElementById("audioimgdislike").onclick = null;
		}
	});
}

// ]]>

// <![CDATA[
function sendAudioRate(adId, rateVal, totalRatePoint, val) {

	totalRatePoint = parseInt(totalRatePoint) + parseInt(val);
	rateVal = parseInt(rateVal);
	rateVal += 1;
	var ratePointval = document.getElementById("ratePoint");
	document.audioGrantValue.ratePoint.value = ratePointval.value+""+adId+"|"+val+",";
	var avgVal = totalRatePoint / rateVal;
	if (avgVal != 0)
		avgVal = Math.round(avgVal * 10) / 10;

	document.getElementById('audiorateVal').value = avgVal + " average";

	$('#audioAdRatingDiv').modal('hide')
	$.ajax({
		url : "./rateCount?adId=" + adId + "&rateCount=" + rateVal
				+ "&totalRatePoint=" + totalRatePoint,
		crossDomain : true,
		type : "POST",
		success : function(data) {
			document.getElementById('audioratePoint').value = rateVal
					+ " ratings";
			document.getElementById("audRate").onclick = null;
		}
	});
}
// ]]>
