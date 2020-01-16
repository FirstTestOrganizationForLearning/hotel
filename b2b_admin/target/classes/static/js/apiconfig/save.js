layui.use(['layer', 'form'], function () {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form;
    //表单验证
    // form.verify({
    //     firstpwd: [/(.+){6,12}$/, '密码必须6到12位'],
    //     secondpwd: function(value) {
    //         if(value != $("#firstpwd").val()){
    //             $("#secondpwd").val("");
    //             return '确认密码与密码不一致';
    //         }
    //     },
    //     productCodes: function(value){
    //         if(value == ''){
    //             return "请选择系统分配";
    //         }
    //     },
    //     sysqx: function(value){
    //         if(value == ''){
    //             return "请选择权限";
    //         }
    //     }
    // });

    //提交监听事件
    form.on('submit(save)', function (data) {
        params = data.field;
        //alert(JSON.stringify(params))
        submit($,params);
        return false;
    });






})
//提交
function submit($,params){
    $.post('/apiConfig/save', params, function (res) {
        if (res.status==0) {
            layer.alert(res.msg)
            CloseWin();

        // }else if(res.status==1){
        //     layer.msg(res.message,{icon:0},function(){
        //         parent.location.href='/apiConfig/';
        //         CloseWin();
        //     })
        }else{
                layer.alert(res.msg)
                location.reload(); // 页面刷新
                return false

        }
    }, 'json');
}
//关闭页面
function CloseWin(){
    parent.location.reload(); // 父页面刷新
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

