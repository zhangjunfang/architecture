$(function() {
    // 点击标题排序
    $('table thead th a.sort').on('click', function() {
        var self = $(this);
        var i_class = $('i', self).attr('class');
        $('table thead th a.sort i').removeClass('icon-caret-up').removeClass('icon-caret-down');

        if (i_class == 'icon-caret-down') {
            $('i', self).removeClass('icon-caret-down').addClass('icon-caret-up');
        }
        else {
            $('i', self).removeClass('icon-caret-up').addClass('icon-caret-down');
        }
        return false;
    })

    // table行选中
    $('.table-single > tbody > tr').on('click', function() {
        var self_tr = $(this);
        if (self_tr.hasClass('selected_tr')) {
            self_tr.removeClass('selected_tr');
            return;
        }

        self_tr.parent().find('tr').removeClass('selected_tr');
        self_tr.addClass('selected_tr');
    })

    $('.table-double > tbody > tr').on('click', function() {
        var self_tr = $(this);
        if (self_tr.hasClass('selected_tr')) {
            self_tr.removeClass('selected_tr');
            return;
        }
        self_tr.addClass('selected_tr');
    })
    
    //移除table的一行
    $('body').on('click', '.remove_tr', function(){
        $(this).parent().parent().remove();
    })

    // 点击进入会议室
    $('.enter-meeting').on('click', function() {
        var self = $(this);
        var txt = self.html();
        var warn_text = (txt == '进入') ? '退出' : '进入';
        $('.enter-meeting').html('进入');
        self.html(warn_text);        
        var meeting_name = self.parent().parent().find('td:first').html();
        if (txt == '进入') {
        	$.cookie('meeting_name', meeting_name, {path: '/' });
        	$(".cmt", parent.document).html("<i class='icon-group'></i> " + meeting_name);        
            $(".menu-wrap", parent.document).find('dt.limit').removeClass('disabled');
            $(".menu-wrap", parent.document).find('dl:eq(1)').addClass('active');
            $(".menu-wrap", parent.document).find('dl:eq(1) > dd:first a').removeClass('active');
            $(".menu-wrap", parent.document).find('dl:eq(1) > dd:eq(1) a').addClass('active'); 
         // $(".menu-wrap", parent.document).find('dl:eq(1) > dd:first a')[0].click();
        } else {
        	$.cookie('meeting_name', '', { expires: -1, path: '/' }); 
        	$(".cmt", parent.document).html("<i class='icon-group'></i>未选择 ");
         // $(".menu-wrap", parent.document).find('.active').removeClass('active');
         // $(".menu-wrap", parent.document).find('dt.limit').addClass('disabled');
        }
    })

    // 滚动滚动条时，设置标题浮动

})