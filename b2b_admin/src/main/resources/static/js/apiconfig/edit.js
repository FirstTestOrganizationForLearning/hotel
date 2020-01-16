layui.use(['layer', 'form'], function () {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form;
    //提交监听事件
    form.on('submit(edit)', function (data) {
        params = data.field;
        //alert(JSON.stringify(params))
        submit($,params);
        return false;
    });


})
//提交
function submit($,params){
    $.post('/apiConfig/edit', params, function (res) {
        var msg=res.msg;
        if (res.status==0) {
            layer.alert(msg, function (index) {
                CloseWin();
            });
        }else{
            layer.alert(msg ,function (index) {
                location.reload(); // 页面刷新
                return false
            });
        }
    }, 'json');
}
//关闭页面
function CloseWin(){
    parent.location.reload(); // 父页面刷新
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

