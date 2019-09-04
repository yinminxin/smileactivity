
$(".include-module").each(function(){
	var item = $(this);
	$.get(item.attr("data-include"),function(result){
		item.before(result);
		item.remove();
	});
});

