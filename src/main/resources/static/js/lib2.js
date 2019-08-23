$(document).ready(function () {
    $.each($("#aside > ul > li > a"),function (e,t) {
        $(t).click(function () {
            $.ajax({
                url:$(t).attr("href"),
                async:false,
                type:"get",
                success:function(data){
                    data = JSON.parse(data);
                    if(data["code"] === 200){
                        setMainTable(data);
                    }else{
                        alert(data["message"]);
                    }
                }
            });
            return false;
        });
    });
});
function cleanMain() {
    var main = document.getElementsByTagName("main")[0];
    var body = document.getElementsByTagName("body")[0];
    main.remove();
    main = document.createElement("main");
    body.append(main);
    return main;
}
function setMainTable(json) {
    var title = json.title;
    var count = json.count;
    var page_no = json.pageNo;
    var page_size = json.pageSize;
    var dataTitle = json.dataTitle;
    var data = json.data;

    var list = getTableWidthList(title);
    var main = cleanMain();
    addListButtonBar(title,page_no,page_size);
    var table = document.createElement("table");
    main.appendChild(table);
    table.setAttribute("id", "main_table");

    var trth = document.createElement("tr");
    table.appendChild(trth);
    var a = 0;
    for (var childTitle in dataTitle) {
        var th = document.createElement("th");
        trth.appendChild(th);
        th.setAttribute("name", childTitle);
        var thText = document.createTextNode(dataTitle[childTitle]);
        th.appendChild(thText);

        th.style.width = list[a];
        a++;
    }
    for (var childData in data) {
        var trtd = document.createElement("tr");
        table.appendChild(trtd);
        var titleName = document.getElementById("main_table").firstElementChild.childNodes;
        for (var x = 0; x < titleName.length; x++) {
            var td = document.createElement("td");
            var name = titleName[x].getAttribute("name");
            var tdText = document.createTextNode(data[childData][name]);
            td.appendChild(tdText);
            td.setAttribute("title", data[childData][name])
            td.setAttribute("name", name);
            trtd.appendChild(td);
        }
    }
    $("#main_table > tr").click(function () {
        $.ajax(
            {
                url:title+"/"+this.firstElementChild.getAttribute("title"),
                type:"get",
                async:false,
                success:function (dataX) {
                    dataX = JSON.parse(dataX);
                    if(dataX["code"] === 200){
                        openDetail(dataX,page_no,page_size);
                    }else{
                        alert(dataX["message"]);
                    }
                }
            }
        );
        return false;
    }
    );
    setPageChooseBar(title,count, parseInt(page_no), parseInt(page_size));
}
function getTableWidthList(name) {
    if (name === "notice") {
        return ["5%", "65%", "15%", "15%"];
    } else if (name === "employee") {
        return ["10%", "20%", "20%", "20%", "20%", "10%"];
    }
}
function addListButtonBar(title,page_no,page_size){
    var main = document.getElementsByTagName("main")[0];
    var bar = document.createElement("div");
    var addButton = document.createElement("button");
    addButton.setAttribute("id","addButton");
    addButton.setAttribute("type","button");
    addButton.appendChild(document.createTextNode("添加"));
    main.appendChild(bar);
    bar.appendChild(addButton);
    $("#addButton").click(function(){
        $.ajax({
            url:title+"/create",
            type:"get",
            async:false,
            success:function(data){
                openCreator(data,title,page_no,page_size);
            }
        });
        return false;
    })
}
function setPageChooseBar(title,count, page_no, page_size) {
    var main = document.getElementsByTagName("main")[0];
    var pageChooseBar = document.createElement("div");
    main.appendChild(pageChooseBar);
    pageChooseBar.setAttribute("id", "main_page_choose_bar");

    var countBar = document.createElement("div");
    countBar.setAttribute("id", "count_bar");
    var pageNoBar = document.createElement("div");
    pageNoBar.setAttribute("id", "page_number_bar");

    pageChooseBar.appendChild(countBar);
    pageChooseBar.appendChild(pageNoBar);

    //总条数bar
    var countText0 = document.createTextNode("共" + count + "条,当前每页");
    var pageSizeChooser = document.createElement("select");
    pageSizeChooser.setAttribute("id", "page_size");
    var option10 = document.createElement("option");
    option10.setAttribute("value", "10");
    var option10Text = document.createTextNode("10");
    option10.appendChild(option10Text);
    var option20 = document.createElement("option");
    option20.setAttribute("value", "20");
    var option20Text = document.createTextNode("20");
    option20.appendChild(option20Text);
    var option30 = document.createElement("option");
    option30.setAttribute("value", "30");
    var option30Text = document.createTextNode("30");
    option30.appendChild(option30Text);
    var option40 = document.createElement("option");
    option40.setAttribute("value", "40");
    var option40Text = document.createTextNode("40");
    option40.appendChild(option40Text);
    var option50 = document.createElement("option");
    option50.setAttribute("value", "50");
    var option50Text = document.createTextNode("50");
    option50.appendChild(option50Text);
    pageSizeChooser.appendChild(option10);
    pageSizeChooser.appendChild(option20);
    pageSizeChooser.appendChild(option30);
    pageSizeChooser.appendChild(option40);
    pageSizeChooser.appendChild(option50);
    var countText1 = document.createTextNode("条");

    countBar.appendChild(countText0);
    countBar.appendChild(pageSizeChooser);
    countBar.appendChild(countText1);
    //设置每页条数
    pageSizeChooser.value = page_size;

    //页码bar
    var max_page_no;
    if (count % page_size === 0) {
        max_page_no = count / page_size;
    } else {
        max_page_no = (count - (count % page_size)) / page_size + 1;
    }
    var firstPageNo = document.createElement("a");
    firstPageNo.setAttribute("value", "1");
    var firstPageNoText = document.createTextNode("首页");
    firstPageNo.appendChild(firstPageNoText);

    pageNoBar.appendChild(firstPageNo);
    if (max_page_no > 1) {
        if ((page_no - 3) > 1) {
            var left_span = document.createElement("span");
            var left_dots = document.createTextNode("...");
            left_span.appendChild(left_dots);
            pageNoBar.appendChild(left_span);
        }
        for (var x = 2; x > 0; x--) {
            var left_page_no = page_no - x;
            if (left_page_no <= 1) { continue; }
            var leftPageNo = document.createElement("a");
            leftPageNo.setAttribute("value", left_page_no);
            var leftPageNoText = document.createTextNode(left_page_no);
            leftPageNo.appendChild(leftPageNoText);
            pageNoBar.appendChild(leftPageNo);
        }
        if (page_no !== 1 && page_no !== max_page_no) {
            var thisPageNo = document.createElement("a");
            thisPageNo.setAttribute("value", page_no);
            var thisPageNoText = document.createTextNode(page_no);
            thisPageNo.appendChild(thisPageNoText);
            pageNoBar.appendChild(thisPageNo);
        }
        for (var x = 1; x < 3; x++) {
            var right_page_no = page_no + x;
            if (right_page_no >= max_page_no) { break; }
            var rightPageNo = document.createElement("a");
            rightPageNo.setAttribute("value", right_page_no);
            var rightPageNoText = document.createTextNode(right_page_no);
            rightPageNo.appendChild(rightPageNoText);
            pageNoBar.appendChild(rightPageNo);
        }
        if ((page_no + 3) < max_page_no) {
            var right_span = document.createElement("span");
            var right_dots = document.createTextNode("...");
            right_span.appendChild(right_dots);
            pageNoBar.appendChild(right_span);
        }
        var maxPageNo = document.createElement("a");
        maxPageNo.setAttribute("value", max_page_no);
        var maxPageNoText = document.createTextNode(max_page_no);
        maxPageNo.appendChild(maxPageNoText);
        pageNoBar.appendChild(maxPageNo);
    }

    var list = pageNoBar.childNodes;
    for (var x = 0; x < list.length; x++) {
        if (list[x].getAttribute("value") == page_no) {
            list[x].style.backgroundColor = "white";
            list[x].style.color = "rgba(64, 77, 255, 0.486)";
            break;
        }
    }
    $("#page_number_bar > a").click(function () {
        var pageNo = $(this).attr("value");
        var pageSize = $("#page_size")[0].value;
        var ajaxUrl = title + "?pageNo="+pageNo+"&pageSize="+pageSize;
        $.ajax({
            url : ajaxUrl,
            async:false,
            type:"get",
            success:function (dataX) {
                dataX = JSON.parse(dataX);
                setMainTable(dataX);
            }
        });
        return false;
    });
}
function openCreator(data,title,page_no,page_size) {
    var form = document.createElement("form");
    form.setAttribute("id", "form");
    form.setAttribute("name", title);
    var main = cleanMain();

    main.appendChild(form);
    for (var x in data) {
        var p = document.createElement("p");
        var input;
        if(x.includes("Data")){continue;}
        if(data[x+"Data"] !== undefined){
            input = document.createElement("select");
            xData = data[x+"Data"];
            for(var y in xData){
                var option = document.createElement("option");
                var yText = document.createTextNode(xData[y]);
                option.appendChild(yText);
                option.setAttribute("value",y);
                input.appendChild(option);
            }
        }else{
            input = document.createElement("input");
        }
        if (x === "content") {
            input = document.createElement("textarea");
        } else if (x === "email") {
            input.setAttribute("type", "email");
        } else if (x === "password") {
            input.setAttribute("type", "password");
        } else {
            input.setAttribute("type", "text");
        }
        form.appendChild(p);
        p.appendChild(input);
        input.setAttribute("placeholder", data[x]);
        input.setAttribute("name", x);
    }
    var div = document.createElement("div");
    form.appendChild(div);
    var submit = document.createElement("input");
    div.appendChild(submit);
    submit.setAttribute("id","submitButton");
    submit.setAttribute("type", "submit");
    submit.setAttribute("value", "提交");
    $("#submitButton").click(function () {
        $("#form").ajaxSubmit({
            url:title,
            type:"POST",
            async:false,
            success:function (dataX) {
                if(dataX["code"] == 200){
                    alert("创建成功");
                    $.ajax({
                        url:title,
                        async:false,
                        type:"get",
                        success:function(dataY){
                            dataY = JSON.parse(dataY);
                            if(dataY["code"] === 200){
                                setMainTable(dataY);
                            }else{
                                alert(dataY["message"]);
                            }
                        }
                    });
                }else{
                    alert(dataX["message"]);
                }
            }
        });
        return false;
    })
}
function openDetail(data,page_no,page_size) {
    var main = cleanMain();
    var form = document.createElement("form");
    main.appendChild(form);
    form.setAttribute("id", "detail");
    for (var b in data["dataTitle"]) {
        if(b.includes("Data")){continue;}
        var label = document.createElement("label");
        var text = document.createTextNode(data["dataTitle"][b] + ":");
        label.appendChild(text);

        var textArea;
        if(data[b+"Data"] == undefined){
            textArea = document.createElement("textarea");
            var textDetail;
            if(data["data"][b] == undefined){
                textDetail = document.createTextNode("");
            }else{
                textDetail = document.createTextNode(data["data"][b]);
            }
            textArea.style.height = textArea.scrollHeight;
            textArea.appendChild(textDetail);
        }else{
            textArea = document.createElement("select");
            for(var c in data[b+"Data"]){
                var option = document.createElement("option");
                var optionText =document.createTextNode(data[b+"Data"][c]);
                option.setAttribute("value",c);
                option.appendChild(optionText);
                if(data[b+"Data"][c] === data["data"][b]){
                    option.setAttribute("selected","selected");
                }
                textArea.appendChild(option);
            }
        }
        textArea.setAttribute("name", b);
        textArea.setAttribute("class","content");

        var p = document.createElement("p");
        var titleDiv = document.createElement("div");
        titleDiv.appendChild(label);
        titleDiv.setAttribute("class","title");
        p.appendChild(titleDiv);
        p.appendChild(textArea);
        form.appendChild(p);
    }
    var updateButton = document.createElement("input");
    updateButton.setAttribute("id", "update");
    updateButton.setAttribute("type", "submit");
    updateButton.setAttribute("value", "提交更改");
    var deleteButton = document.createElement("input");
    deleteButton.setAttribute("id", "delete");
    deleteButton.setAttribute("type", "submit");
    deleteButton.setAttribute("value", "删除");
    form.appendChild(updateButton);
    form.appendChild(deleteButton);
    $("#update").click(function () {
        $("#detail").ajaxSubmit({
            url:data["title"]+"/"+data["data"]["id"],
            type:"PUT",
            async:false,
            success:function (dataX) {
                if(dataX["code"] == 200){
                    alert("修改成功");
                    $.ajax({
                        url:data["title"]+"/"+data["data"]["id"],
                        type:"get",
                        async:false,
                        success:function (dataY) {
                            dataY = JSON.parse(dataY);
                            if(dataY["code"] == 200){
                                openDetail(dataY,page_no,page_size);
                            }else{
                                alert(data["message"]);
                            }
                        }
                    });
                }else{
                    alert(dataX["message"]);
                }
            }
        });
        return false;
    });
    $("#delete").click(function () {
        $.ajax({
            url:data["title"]+"/"+data["data"]["id"],
            type:"delete",
            async:false,
            data:{"pageNo":page_no,"pageSize":page_size},
            success:function (dataX) {
                if(dataX["code"] == 200){
                    alert("删除成功,即将返回列表");
                    $.ajax({
                        url:data["title"],
                        async:false,
                        type:"get",
                        success:function (dataY){
                            dataY = JSON.parse(dataY);
                            if(dataY["code"] === 200){
                                setMainTable(dataY);
                            }else{
                                alert(dataY["message"]);
                            }
                        }
                    });
                }else{
                    alert(dataX["message"]);
                }
            }
        });
        return false;
    });
    $.each($("#detail textarea"),function (i,each) {
        $(each).css("height",each.scrollHeight + "px");
    })
}