var page_size = 20;
$(document).ready(function () {
    $.each($("#aside > ul > li > a"),function (e,t) {
        $(t).click(function () {
            $.ajax({
                url:$(t).attr("href"),
                async:false,
                type:"get",
                data:{"pageSize":page_size},
                success:function(data){
                    if(data["code"] === 200){
                        setMainTable(data);
                    }else{
                        alert(data["message"]);
                    }
                }
            });
            $.each($("#aside > ul > li > a"),function(a,b){
                b.style.color = "";
            });
            t.style.color = "#005d80";
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
    var page_no = json.pageNo;

    cleanMain();
    addSearchpanel(title);
    addListButtonBar(title,page_no);
    setTable(json);
}
function setTable(json){
    var title = json.title;
    var count = json.count;
    var page_no = json.pageNo;
    var dataTitle = json.dataTitle;
    var data = json.data;
    var list = getTableWidthList(title);
    $("#main_table").remove();
    $("#main_page_choose_bar").remove();
    var table = document.createElement("table");
    var main = document.getElementsByTagName("main")[0];
    main.appendChild(table);

    table.setAttribute("id", "main_table");

    var trth = document.createElement("tr");
    table.appendChild(trth);
    var a = 0;
    var mulitiSelectTh = document.createElement("th");
    mulitiSelectTh.setAttribute("class","checkbox");    
    trth.appendChild(mulitiSelectTh);
    for (var childTitle in dataTitle) {
        var th = document.createElement("th");
        trth.appendChild(th);
        th.setAttribute("name", childTitle);
        th.appendChild(document.createTextNode(dataTitle[childTitle]));

        th.style.width = list[a];
        a++;
    }
    for (var childData in data) {
        var trtd = document.createElement("tr");
        table.appendChild(trtd);
        var selectTdInput = document.createElement("input");
        selectTdInput.setAttribute("type","checkbox");
        selectTdInput.setAttribute("no",data[childData]["id"]);
        var selectTd = document.createElement("td");
        selectTd.setAttribute("class","checkbox");
        selectTd.setAttribute("title", data[childData]["id"])
        selectTd.appendChild(selectTdInput);
        trtd.appendChild(selectTd);
        var titleName = document.getElementById("main_table").firstElementChild.childNodes;
        for (var x = 1; x < titleName.length; x++) {
            var td = document.createElement("td");
            var name = titleName[x].getAttribute("name");
            td.appendChild(document.createTextNode(data[childData][name]));
            td.setAttribute("title", data[childData]["id"])
            td.setAttribute("name", name);
            trtd.appendChild(td);
        }
    }
    $.each($("#main_table > tr > td"),function(a,b){
        if($(b).attr("class") != "checkbox"){
            $(b).click(function(){
                $.ajax(
                    {
                        url:title+"/"+$(b).attr("title"),
                        type:"get",
                        success:function (dataX) {
                            if(dataX["code"] == 200){
                                openDetail(dataX,page_no,$(b).attr("title"));
                            }else{
                                alert(dataX["message"]);
                            }
                        }
                    }
                );
                return false;
            })
        }

    })
    setPageChooseBar(title,count, parseInt(page_no));
}
function getTableWidthList(name) {
    if (name === "notice") {
        return ["5%", "60%", "15%", "15%"];
    } else if (name === "employee") {
        return ["10%", "20%", "20%", "20%", "20%"];
    }else if(name === "dept"){
        return ["25%","70%"];
    }else if(name === "job"){
        return ["25%","70%"];
    }
}
function addSearchpanel(title){
    var form = document.createElement("form");
    form.setAttribute("id","search");
    var main = document.getElementsByTagName("main")[0];
    main.appendChild(form);
    $.ajax({
        url:title+"/search",
        type:"get",
        success:function(data){
            setList(form,data);
            $.each($("#search > p > select"),function(a,b){
                var option = document.createElement("option");
                option.appendChild(document.createTextNode("请选择"));
                option.setAttribute("value","");
                option.setAttribute("selected","selected");
                b.appendChild(option);
            })
        }
    });
    var searchButton = document.createElement("input");
    searchButton.setAttribute("type","submit");
    searchButton.setAttribute("value","搜索");
    searchButton.style.color = "red";
    var searchButtonDiv = document.createElement("div");
    searchButtonDiv.setAttribute("id","searchButton");
    searchButtonDiv.appendChild(searchButton);
    form.appendChild(searchButtonDiv);
    $("#searchButton > input").click(function(){
        $("#search").ajaxSubmit({
            url:title,
            type:"get",
            success:function(data){
                setTable(data);
            }
        })
        return false;
    });

}
function addListButtonBar(title,page_no){
    var main = document.getElementsByTagName("main")[0];
    var bar = document.createElement("div");
    main.appendChild(bar);

    var addButton = document.createElement("button");
    addButton.setAttribute("id","addButton");
    addButton.setAttribute("type","button");
    addButton.appendChild(document.createTextNode("添加"));
    bar.appendChild(addButton);

    var mulitiDelete = document.createElement("button");
    mulitiDelete.setAttribute("id","mulitiDelete");
    mulitiDelete.setAttribute("type","button");
    mulitiDelete.appendChild(document.createTextNode("批量删除"));
    bar.appendChild(mulitiDelete);
    

    $("#addButton").click(function(){
        $.ajax({
            url:title+"/create",
            type:"get",
            async:false,
            success:function(data){
                openCreator(data,title,page_no);
            }
        });
        return false;
    })
    $("#mulitiDelete").click(function(){
        var message = "";
        $.each($(".checkbox > input"),function(a,b){
            if(b.checked){
                $.ajax({
                    url:title+"/"+$(b).attr("no"),
                    type:"delete",
                    async:false,
                    success:function(data){
                        if(data["code"] == 200){
                            message +=("序号"+ $(b).attr("no") +": 删除成功. \n");

                        }else{
                            message +=("序号"+ $(b).attr("no") + data["message"]+". \n");
                        }
                    },
                })
            }
        })
        alert(message);
        $.ajax({
            url:title,
            async:false,
            type:"get",
            success:function (dataY){
                if(dataY["code"] === 200){
                    setTable(dataY);
                }else{
                    alert(dataY["message"]);
                }
            }
        });
    })
}
function setPageChooseBar(title,count, page_no) {
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
    var pageSizeChooser = document.createElement("select");
    pageSizeChooser.setAttribute("id", "page_size");
    var option10 = document.createElement("option");
    option10.setAttribute("value", "10");
    option10.appendChild(document.createTextNode("10"));
    var option20 = document.createElement("option");
    option20.setAttribute("value", "20");
    option20.appendChild(document.createTextNode("20"));
    var option30 = document.createElement("option");
    option30.setAttribute("value", "30");
    option30.appendChild(document.createTextNode("30"));
    var option40 = document.createElement("option");
    option40.setAttribute("value", "40");
    option40.appendChild(document.createTextNode("40"));
    var option50 = document.createElement("option");
    option50.setAttribute("value", "50");
    option50.appendChild(document.createTextNode("50"));
    pageSizeChooser.appendChild(option10);
    pageSizeChooser.appendChild(option20);
    pageSizeChooser.appendChild(option30);
    pageSizeChooser.appendChild(option40);
    pageSizeChooser.appendChild(option50);

    countBar.appendChild(document.createTextNode("共" + count + "条,当前每页"));
    countBar.appendChild(pageSizeChooser);
    countBar.appendChild(document.createTextNode("条"));
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
    firstPageNo.appendChild(document.createTextNode("首页"));

    pageNoBar.appendChild(firstPageNo);
    if (max_page_no > 1) {
        if ((page_no - 3) > 1) {
            var left_span = document.createElement("span");
            left_span.appendChild(document.createTextNode("..."));
            pageNoBar.appendChild(left_span);
        }
        for (var x = 2; x > 0; x--) {
            var left_page_no = page_no - x;
            if (left_page_no <= 1) { continue; }
            var leftPageNo = document.createElement("a");
            leftPageNo.setAttribute("value", left_page_no);
            leftPageNo.appendChild(document.createTextNode(left_page_no));
            pageNoBar.appendChild(leftPageNo);
        }
        if (page_no !== 1 && page_no !== max_page_no) {
            var thisPageNo = document.createElement("a");
            thisPageNo.setAttribute("value", page_no);
            thisPageNo.appendChild(document.createTextNode(page_no));
            pageNoBar.appendChild(thisPageNo);
        }
        for (var x = 1; x < 3; x++) {
            var right_page_no = page_no + x;
            if (right_page_no >= max_page_no) { break; }
            var rightPageNo = document.createElement("a");
            rightPageNo.setAttribute("value", right_page_no);
            rightPageNo.appendChild(document.createTextNode(right_page_no));
            pageNoBar.appendChild(rightPageNo);
        }
        if ((page_no + 3) < max_page_no) {
            var right_span = document.createElement("span");
            right_span.appendChild(document.createTextNode("..."));
            pageNoBar.appendChild(right_span);
        }
        var maxPageNo = document.createElement("a");
        maxPageNo.setAttribute("value", max_page_no);
        maxPageNo.appendChild(document.createTextNode(max_page_no));
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
    $("#page_size").change(function(){
        var pageNo = page_no;
        page_size = this.value;
        var ajaxUrl = title + "?pageNo="+pageNo+"&pageSize="+page_size;
        $("#search").ajaxSubmit({
            url : ajaxUrl,
            async:false,
            type:"get",
            success:function (dataX) {
                setTable(dataX);
            }
        });
        return false;
    })
    $("#page_number_bar > a").click(function () {
        var pageNo = $(this).attr("value");
        var ajaxUrl = title + "?pageNo="+pageNo+"&pageSize="+page_size;
        $("#search").ajaxSubmit({
            url : ajaxUrl,
            async:false,
            type:"get",
            success:function (dataX) {
                setTable(dataX);
            }
        });
        return false;
    });
}
function openCreator(data,title,page_no) {
    var form = document.createElement("form");
    form.setAttribute("id", "form");
    form.setAttribute("name", title);
    var main = cleanMain();

    main.appendChild(form);
    setList(form,data);
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
                        data:{"pageNo":page_no,"pageSize":page_size},
                        success:function(dataY){
                            if(dataY["code"] === 200){
                                setMainTable(dataY);
                            }else{
                                alert(dataY["message"]);
                            }
                        }
                    });
                }else{
                    var alertMessage = "";
                    for(var message in dataX["message"]){
                        alertMessage += $("#form > p[title='" + message +"'] > div > label").text() + dataX["message"][message] + ".\n";
                    }
                    alert(alertMessage);
                }
            }
        });
        return false;
    })
}
function openDetail(data,page_no,id) {
    var main = cleanMain();
    var form = document.createElement("form");
    main.appendChild(form);
    form.setAttribute("id", "detail");

    var updateButton = document.createElement("input");
    updateButton.setAttribute("id", "update");
    updateButton.setAttribute("type", "submit");
    updateButton.setAttribute("value", "提交更改");
    var deleteButton = document.createElement("input");
    deleteButton.setAttribute("id", "delete");
    deleteButton.setAttribute("type", "submit");
    deleteButton.setAttribute("value", "删除");
    var returnButton = document.createElement("button");
    returnButton.setAttribute("id","return");
    returnButton.setAttribute("type","button");
    returnButton.appendChild(document.createTextNode("返回"));

    form.appendChild(updateButton);
    form.appendChild(deleteButton);
    form.appendChild(returnButton);

    setList(form,data);

    $("#update").click(function () {
        $("#detail").ajaxSubmit({
            url:data["title"]+"/"+id,
            type:"PUT",
            async:false,
            success:function (dataX) {
                if(dataX["code"] == 200){
                    alert("修改成功");
                    $.ajax({
                        url:data["title"]+"/"+id,
                        type:"get",
                        async:false,
                        success:function (dataY) {
                            if(dataY["code"] == 200){
                                openDetail(dataY,page_no,id);
                            }else{
                                alert(data["message"]);
                            }
                        }
                    });
                }else{
                    var alertMessage = "";
                    for(var message in dataX["message"]){
                        alertMessage += $("#detail > p[title='" + message +"'] > div > label").text() + dataX["message"][message] + ".\n";
                    }
                    alert(alertMessage);
                }
            }
        });
        return false;
    });
    $("#delete").click(function () {
        $.ajax({
            url:data["title"]+"/"+id,
            type:"delete",
            async:false,
            success:function (dataX) {
                if(dataX["code"] == 200){
                    alert("删除成功,即将返回列表");
                    $.ajax({
                        url:data["title"],
                        async:false,
                        type:"get",
                        success:function (dataY){
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
    $("#return").click(function(){
        $.ajax({
            url:data["title"],
            async:false,
            type:"get",
            success:function (dataY){
                if(dataY["code"] === 200){
                    setMainTable(dataY);
                }else{
                    alert(dataY["message"]);
                }
            }
        });
    })
    $.each($("#detail textarea"),function (i,each) {
        $(each).css("height",each.scrollHeight + "px");
    })
}

function setList(parent,data){
    for (var b in data["dataTitle"]) {
        var p = document.createElement("p");
        parent.appendChild(p);
        if(b.includes("Data")){continue;}
        var label = document.createElement("label");
        label.appendChild(document.createTextNode(data["dataTitle"][b] + ":"));

        var textArea;
        if(data[b+"Data"] == undefined){
            if(b.includes("date") || b.includes("Date") || b == "birthday"){
                textArea = document.createElement("input");
                textArea.setAttribute("type","date");
                if(data["data"] != undefined){
                    textArea.setAttribute("value",data["data"][b]);
                }
            }else{
                textArea = document.createElement("textarea");
                if(data["data"] != undefined){
                    var textDetail;
                    if(data["data"][b] == undefined){
                        textDetail = document.createTextNode("");
                    }else{
                        textDetail = document.createTextNode(data["data"][b]);
                    }
                    textArea.style.height = textArea.scrollHeight;
                    textArea.appendChild(textDetail);
                }
            }
        }else{
            textArea = document.createElement("select");
            for(var c in data[b+"Data"]){
                var option = document.createElement("option");
                option.setAttribute("value",c);
                option.appendChild(document.createTextNode(data[b+"Data"][c]["name"]));
                if(data["data"] != undefined && data[b+"Data"][c] === data["data"][b]){
                    option.setAttribute("selected","selected");
                }
                textArea.appendChild(option);
            }
        }
        textArea.setAttribute("name", b);
        textArea.setAttribute("class","content");


        var titleDiv = document.createElement("div");
        titleDiv.appendChild(label);
        titleDiv.setAttribute("class","title");
        p.appendChild(titleDiv);
        p.appendChild(textArea);
        p.setAttribute("title",b)
        
    }
}