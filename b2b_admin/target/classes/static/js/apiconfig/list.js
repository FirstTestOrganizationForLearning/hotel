var tableId="api_config_list"
layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'element', 'slider'], function(){
    var laydate = layui.laydate //日期
        ,laypage = layui.laypage //分页
        ,layer = layui.layer //弹层
        ,table = layui.table //表格
        ,carousel = layui.carousel //轮播
        ,upload = layui.upload //上传
        ,element = layui.element //元素操作
        ,slider = layui.slider //滑块
        , $ = layui.jquery  //jquery

    //监听Tab切换
    element.on('tab('+api_config_list+')', function(data){
        layer.tips('切换了 '+ data.index +'：'+ this.innerHTML, this, {
            tips: 1
        });
    });


    //执行一个 table 实例
    table.render({
        elem: '#'+tableId
        ,height: 600
        ,url: '/apiConfig/list' //数据接口
        ,title: '埋点列表'
        ,page:true //开启分页
        ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        // ,totalRow: true //开启合计行
        , limits: [5, 10, 20, 30, 50] //每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。优先级低于 page 参数中的 limits 参数。
        , limit: 10 //每页显示的条数（默认：10）。值务必对应 limits 参数的选项。优先级低于 page 参数中的 limit 参数。
        ,cols: [
            [
                {type: 'checkbox', fixed: 'left'}
                ,{field: 'compid', title: 'ID', width:100, sort: true, fixed: 'left', totalRowText: '合计：'}
                ,{field: 'sid', title: 'SID', width:100, sort: true, fixed: 'left', totalRowText: '合计：'}
                ,{field: 'basePrice', title: '起价', width:80}
                ,{field: 'directPrice', title: '直连报价', width: 90, sort: true, totalRow: true}
                ,{field: 'isBookroom', title: '房型预定检测', width:100, sort: true}
                ,{field: 'createOrder', title: '创建订单', width: 80, sort: true, totalRow: true}
                ,{field: 'submitOrder', title: '提交订单', width:100}
                ,{field: 'payResult', title: '获取支付确认结果', width: 120}
                ,{field: 'orderstatusChange', title: '监测订单状态变化', width: 120}
                ,{field: 'orderDetail', title: '获取订单详情', width: 125, sort: true, totalRow: true}
                ,{field: 'cancelOrder', title: '取消订单', width: 120, sort: true, totalRow: true}
                ,{field: 'isExceed', title: '是否允许超过频次', width: 135, totalRow: true,
                templet: function (d) {
                    var isExceed = d.isExceed;
                    var isExceedStr = "";
                    if (isExceed == 1) {
                        isExceedStr = "允许";
                    } else if (isExceed == 0) {
                        isExceedStr = "不允许";
                    }
                    return isExceedStr;
                }
            }
                ,{field: 'chargStandard', title: '收费标准', width: 135,  totalRow: true}
                ,{fixed: 'right', width: 165, align:'center', toolbar: '#barDemo'}
            ]
        ]
        , request:
            {
                pageName: 'pageNum' //页码的参数名称，默认：page
                ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
            }
        , response:
            {
                statusName: 'code' //数据状态的字段名称，默认：code
                , countName: 'count'//返回条数
                , msgName: 'message' //规定状态信息的字段名称，默认：msg
                , statusCode: 0 //成功的状态码，默认：0
            }
    });

    //监听头工具栏事件
    table.on('toolbar(test)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取选中的数据
        switch(obj.event){
            case 'add':
                layer.open({
                    type: 2
                    , title: '添加供应商频率限制配置'
                    , closeBtn: 1
                    , offset: '1px'
                    , anim: 1
                    , area: ['100%', '100%']
                    // ,skin: 'layui-layer-nobg' //没有背景色
                    , shadeClose: true
                    , content: '/apiConfig/add'
                });
                break;
            case 'update':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else if(data.length > 1){
                    layer.msg('只能同时编辑一个');
                } else {
                    //layer.alert('编辑 [id]：'+ checkStatus.data[0].id);
                    layer.open({
                        type: 2
                        , title: '编辑供应商频率限制配置'
                        , closeBtn: 1
                        , offset: '1px'
                        , anim: 1
                        , area: ['100%', '100%']
                        // ,skin: 'layui-layer-nobg' //没有背景色
                        , shadeClose: true
                        , content: '/apiConfig/edit/'+data[0].compid+'/'+data[0].sid
                    });
                    break;
                }
                break;
            case 'delete':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else {
                    layer.msg('删除');
                }
                break;
        };
    });

    //监听行工具事件
    table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detail'){
            layer.msg('查看操作');
        } else if(layEvent === 'del'){
            layer.confirm('真的删除行么', function(index){
                del($,data.compid,data.sid)
                obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
            });
        } else if(layEvent === 'edit'){
            layer.open({
                type: 2
                , title: '编辑供应商频率限制配置'
                , closeBtn: 1
                , offset: '1px'
                , anim: 1
                , area: ['100%', '100%']
                // ,skin: 'layui-layer-nobg' //没有背景色
                , shadeClose: true
                , content: '/apiConfig/edit/'+data.compid+'/'+data.sid
            });
        }
    });

    //将日期直接嵌套在指定容器中
    var dateIns = laydate.render({
        elem: '#laydateDemo'
        ,position: 'static'
        ,calendar: true //是否开启公历重要节日

        ,done: function(value, date, endDate){
            if(date.year == 2017 && date.month == 11 && date.date == 30){
                dateIns.hint('一不小心就月底了呢');
            }
        }
        ,change: function(value, date, endDate){
            layer.msg(value)
        }
    });
    //滑块
    var sliderInst = slider.render({
        elem: '#sliderDemo'
        ,input: true //输入框
    });

});
function del($,id,sid){
    $.post('/apiConfig/del/'+id+'/'+sid, function (res) {
        if (res.status==0) {
            layer.alert("删除成功")
        }else{
            layer.alert("删除失败")
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