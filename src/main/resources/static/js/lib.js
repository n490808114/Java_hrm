$(function () {
    $(document).ajaxError(
        function (event,xhr,options,exc) {
            if(xhr.status == undefined){
                return;
            }
            switch (xhr.status) {
                case 403:
                    reLogin();
                    break;
                case 400:
                    showErrorMessage(JSON.parse(xhr.responseText)["message"]);
            }
        }
    );
    $(document).ajaxSend(
        function (event,xhr,options) {

            xhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("token"));
        }
    )
});
function reLogin(){
    addCoverDiv();
    addLoginForm();
    showErrorMessage("可能失败的原因：<br>1: 未登录<br>2:上次登录超过1个小时,登录状态已过期<br>3:您没有访问该链接的权限");

}
function addErrorMessageDiv(){
    var errorMsg = document.createElement("div");
    errorMsg.setAttribute("id","errorMsg");
    errorMsg.style.display = "none";
    document.getElementsByTagName("body")[0].appendChild(errorMsg);
}
function showErrorMessage(message){
    $("#errorMsg").html(message).show(300).delay(1500).hide(200);
}
function setLogined(){
    var payload = JSON.parse(base64decode(localStorage.getItem("token").split(".")[1]));
    $.ajax({
        url:"user/" + payload["id"] + "/name",
        type:"get",
        async:false,
        success:function(data){
            $("#userName").text(data);
        }
    })
    $("#userControllerLoginButton").attr("go","loginOut");
    
    $("#userRole").text(payload["roles"]);
    $("#userControllerLoginButton").text("注销");
    if(localStorage.getItem("loginStatusTimeOutClock") != null){
        clearTimeout(localStorage.getItem("loginStatusTimeOutClock"));
    }
    var loginStatusTimeOutClock = window.setTimeout(setLoginOuted,payload["exp"]*1000-new Date().getTime());
    localStorage.setItem("loginStatusTimeOutClock",loginStatusTimeOutClock);
}
function setLoginOuted(){
    localStorage.removeItem("token");
    $("#userControllerLoginButton").attr("go","loginIn");
    $("#userName").text("未登录");
    $("#userRole").text("无");
    $("#userControllerLoginButton").text("登录");
}
$(document).ready(function () {
    addErrorMessageDiv();
    if(localStorage.getItem("token")!= null){
        setLogined();
    }
    $("#userControllerLoginButton").click(function(){
        if(this.getAttribute("go") == "loginIn"){
            addCoverDiv();
            addLoginForm();
        }
        if(this.getAttribute("go") == "loginOut"){
            setLoginOuted();
        }
    })
    $.each($("#aside > ul > li > a"),function (e,t) {
        $(t).click(function () {
            $.ajax({
                url:$(t).attr("href"),
                async:false,
                type:"get",
                data:{"pageSize":(localStorage.getItem("page_size")==null?20:localStorage.getItem("page_size"))},
                success:function(data){
                    if(data["code"] === 200){
                        setMainTable(data);
                    }else{
                        showErrorMessage(data["message"]);
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

//添加背景灰色遮挡
function addCoverDiv() {
    var body = document.getElementsByTagName("body")[0];

    var divCover = document.createElement("div");
    body.appendChild(divCover);
    divCover.setAttribute("id", "divCover");
    $("#divCover").click(function () {
        closeCoverDiv();
    });
}
function closeCoverDiv() {
    document.getElementById("divCover").remove();
    $("#loginOrRegisterForm").remove();

}
function addLoginForm(){
    var loginForm = document.createElement("form");
    loginForm.setAttribute("id","login");
    var loginH1 = document.createElement("h1");
    loginH1.appendChild(document.createTextNode("登录"));
    loginForm.appendChild(loginH1);
    var loginFieldSet = document.createElement("fieldset");

    var username = document.createElement("input");
    username.setAttribute("type","text");
    username.setAttribute("placeholder","用户名/邮箱");
    username.setAttribute("name","username");

    var password = document.createElement("input");
    password.setAttribute("type","password");
    password.setAttribute("placeholder","密码");
    password.setAttribute("name","password");

    var usernameLabel = document.createElement("label");
    var passwordLabel = document.createElement("label");
    usernameLabel.appendChild(username);
    passwordLabel.appendChild(password);

    loginFieldSet.appendChild(usernameLabel);
    loginFieldSet.appendChild(passwordLabel);

    loginForm.appendChild(loginFieldSet);

    var loginButton = document.createElement("input");
    loginButton.setAttribute("type","submit");
    loginButton.setAttribute("id","loginButton");
    var loginDiv = document.createElement("div");
    loginDiv.appendChild(loginButton);
    loginForm.appendChild(loginDiv);

    var registerForm = document.createElement("form");
    registerForm.setAttribute("id","register");
    var registerH1 = document.createElement("h1");
    registerH1.appendChild(document.createTextNode("注册"));
    registerForm.appendChild(registerH1);
    var registerFieldSet = document.createElement("fieldset");

    var registerUsername = document.createElement("input");
    registerUsername.setAttribute("type","input");
    registerUsername.setAttribute("placeholder","用户名");
    registerUsername.setAttribute("name","username");

    var registerEmail = document.createElement("input");
    registerEmail.setAttribute("type","email");
    registerEmail.setAttribute("placeholder","邮箱");
    registerEmail.setAttribute("name","email");

    var registerPassword = document.createElement("input");
    registerPassword.setAttribute("type","password");
    registerPassword.setAttribute("placeholder","请输入密码");
    registerPassword.setAttribute("name","password");
    registerPassword.setAttribute("confirm","0");

    var registerConfirmPassword = document.createElement("input");
    registerConfirmPassword.setAttribute("type","password");
    registerConfirmPassword.setAttribute("placeholder","请重复输入密码");
    registerConfirmPassword.setAttribute("confirm","1");

    var registerUsernameLabel = document.createElement("label");
    var registerEmailLabel = document.createElement("label");
    var registerPasswordLabel = document.createElement("label");
    var registerConfirmPasswordLabel = document.createElement("label");
    registerUsernameLabel.appendChild(registerUsername);
    registerEmailLabel.appendChild(registerEmail);
    registerPasswordLabel.appendChild(registerPassword);
    registerConfirmPasswordLabel.appendChild(registerConfirmPassword);

    registerFieldSet.appendChild(registerUsernameLabel);
    registerFieldSet.appendChild(registerEmailLabel);
    registerFieldSet.appendChild(registerPasswordLabel);
    registerFieldSet.appendChild(registerConfirmPasswordLabel);

    registerForm.appendChild(registerFieldSet);

    var registerButton = document.createElement("input");
    registerButton.setAttribute("type","submit");
    registerButton.setAttribute("id","registerButton");
    var registerDiv = document.createElement("div");
    registerDiv.appendChild(registerButton);
    registerForm.appendChild(registerDiv);

    var div = document.createElement("div");
    div.setAttribute("id","loginOrRegisterForm");
    div.appendChild(loginForm);
    div.appendChild(registerForm);

    var closeButton = document.createElement("button");
    closeButton.setAttribute("id","loginOrRegisterFormCloseButton");
    closeButton.appendChild(document.createTextNode("关闭"));


    var changeFormButton = document.createElement("button");
    changeFormButton.setAttribute("id","loginOrRegisterFormChangeFormButton");
    changeFormButton.appendChild(document.createTextNode("注册"));
    registerForm.style.display = "none";


    var div = document.createElement("div");
    div.setAttribute("id","loginOrRegisterForm");
    div.appendChild(loginForm);
    div.appendChild(registerForm);
    div.appendChild(closeButton);
    div.appendChild(changeFormButton);

    document.getElementsByTagName("body")[0].appendChild(div);


    $("#loginButton").click(function () {
        $("#login").ajaxSubmit({
            url:"/login",
            type:"post",
            async:false,
            success:function (data) {
                if(data["token"] != undefined){
                    localStorage.setItem("token",data["token"]);
                    showErrorMessage("登陆成功");
                    closeCoverDiv();
                    setLogined();
                }

            }
        });
        return false;
    })
    $("#loginOrRegisterFormCloseButton").click(function(){
        closeCoverDiv();return false;
    })
    $("#loginOrRegisterFormChangeFormButton").click(function () {
        if(registerForm.style.display == "none"){
            registerForm.style.display = "";
            loginForm.style.display = "none";
            $(this).text("登录");
        }else if(loginForm.style.display == "none"){
            loginForm.style.display = "";
            registerForm.style.display = "none";
            $(this).text("注册");
        }
        return false;
    });
    $("#registerButton").click(function(){
        if($("#register input[confirm='0']").val() === "" ){
            showErrorMessage("密码为空,请输入密码！");
        }else if($("#register input[confirm='0']").val() != $("#register input[confirm='1']").val()){
            showErrorMessage("密码输入不一致请重新输入");
            $("[confirm='0']").val("");
            $("[confirm='1']").val("");
        }else if($("#register input[name='username']").val() == ""){
            showErrorMessage("用户名为空,请输入用户名");
        }else{
            $("#register").ajaxSubmit({
                url:"register",
                type:"post",
                async:false,
                success:function(data){
                    if(data["token"] != undefined){
                        localStorage.setItem("token",data["token"]);
                        showErrorMessage("注册成功,并自动登录!");
                        closeCoverDiv();
                        setLogined();
                    }
                    if(data["message"] != undefined){
                        showErrorMessage(data["message"]);
                    }

                }
            })
        }

        return false;
    })
}
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
        selectTdInput.setAttribute("no",childData);
        var selectTd = document.createElement("td");
        selectTd.setAttribute("class","checkbox");
        selectTd.setAttribute("title", childData);
        selectTd.appendChild(selectTdInput);
        trtd.appendChild(selectTd);
        var titleName = document.getElementById("main_table").firstElementChild.childNodes;
        for (var x = 1; x < titleName.length; x++) {
            var td = document.createElement("td");
            var name = titleName[x].getAttribute("name");
            td.appendChild(document.createTextNode(data[childData][name]));
            td.setAttribute("title", childData)
            td.setAttribute("name", name);
            trtd.appendChild(td);
        }
    }
    if(Object.getOwnPropertyNames(data).length == 0){showErrorMessage(json.message);}
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
                                showErrorMessage(dataX["message"]);
                            }
                        }
                    }
                );
                return false;
            })
        }

    });
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
    }else if(name === "document"){
        return ["5%", "60%", "15%", "15%"];
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
    searchButton.style.color = "#c73a48";
    var searchButtonDiv = document.createElement("div");
    searchButtonDiv.setAttribute("id","searchButton");
    searchButtonDiv.appendChild(searchButton);
    form.appendChild(searchButtonDiv);
    $("#searchButton > input").click(function(){
        $("#search").ajaxSubmit({
            url:title + "?pageSize="+(localStorage.getItem("page_size")==null?20:localStorage.getItem("page_size")),
            type:"get",
            success:function(data){
                setTable(data);
            }
        });
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
    });
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
                            message +=("序号"+ $(b).attr("no") +": 删除成功. <br>");

                        }else{
                            message +=("序号"+ $(b).attr("no") + data["message"]+". <br>");
                        }
                    }
                })
            }
        });
        showErrorMessage(message);
        $("#search").ajaxSubmit({
            url:title + "?pageSize="+(localStorage.getItem("page_size")==null?20:localStorage.getItem("page_size")),
            type:"get",
            success:function(data){
                setTable(data);
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
    var page_size = localStorage.getItem("page_size")==null?20:localStorage.getItem("page_size");
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
        localStorage.setItem("page_size",this.value);
        $("#search").ajaxSubmit({
            url:title + "?pageSize="+(localStorage.getItem("page_size")==null?20:localStorage.getItem("page_size")),
            async:false,
            type:"get",
            data:{"pageNo":pageNo,"pageSize":this.value},
            success:function (dataX) {
                setTable(dataX);
            }
        });
        return false;
    });
    $("#page_number_bar > a").click(function () {
        var pageNo = $(this).attr("value");
        $("#search").ajaxSubmit({
            url:title + "?pageSize="+(localStorage.getItem("page_size")==null?20:localStorage.getItem("page_size")),
            async:false,
            type:"get",
            data:{"pageNo":pageNo,"pageSize":(localStorage.getItem("page_size")==null?20:localStorage.getItem("page_size"))},
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
                    showErrorMessage("创建成功");
                    $.ajax({
                        url:title,
                        async:false,
                        type:"get",
                        data:{"pageNo":page_no,"pageSize":(localStorage.getItem("page_size")==null?20:localStorage.getItem("page_size"))},
                        success:function(dataY){
                            if(dataY["code"] === 200){
                                setMainTable(dataY);
                            }else{
                                showErrorMessage(dataY["message"]);
                            }
                        }
                    });
                }else{
                    var showErrorMessageMessage = "";
                    for(var message in dataX["message"]){
                        showErrorMessageMessage += $("#form > p[title='" + message +"'] > div > label").text() + dataX["message"][message] + ".<br>";
                    }
                    showErrorMessage(showErrorMessageMessage);
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

    setList(form,data,id);

    $("#update").click(function () {
        $("#detail").ajaxSubmit({
            url:data["title"]+"/"+id,
            type:"PUT",
            async:false,
            success:function (dataX) {
                if(dataX["code"] == 200){
                    showErrorMessage("修改成功");
                    $.ajax({
                        url:data["title"]+"/"+id,
                        type:"get",
                        async:false,
                        success:function (dataY) {
                            if(dataY["code"] == 200){
                                openDetail(dataY,page_no,id);
                            }else{
                                showErrorMessage(data["message"]);
                            }
                        }
                    });
                }else{
                    var showErrorMessageMessage = "";
                    for(var message in dataX["message"]){
                        showErrorMessageMessage += $("#detail > p[title='" + message +"'] > div > label").text() + dataX["message"][message] + ".<br>";
                    }
                    showErrorMessage(showErrorMessageMessage);
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
                    showErrorMessage("删除成功,即将返回列表");
                    $.ajax({
                        url:data["title"],
                        async:false,
                        type:"get",
                        data:{"pageSize":(localStorage.getItem("page_size")==null?20:localStorage.getItem("page_size"))},
                        success:function (dataY){
                            if(dataY["code"] === 200){
                                setMainTable(dataY);
                            }else{
                                showErrorMessage(dataY["message"]);
                            }
                        }
                    });
                }else{
                    showErrorMessage(dataX["message"]);
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
            data:{"pageSize":(localStorage.getItem("page_size")==null?20:localStorage.getItem("page_size"))},
            success:function (dataY){
                if(dataY["code"] === 200){
                    setMainTable(dataY);
                }else{
                    showErrorMessage(dataY["message"]);
                }
            }
        });
    });
    $.each($("#detail textarea"),function (i,each) {
        $(each).css("height",each.scrollHeight + "px");
    })
}

function setList(parent,data,id){
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
            }else if(b == "file"){
                if(data["data"] != undefined){
                    textArea = document.createElement("a");
                    textArea.appendChild(document.createTextNode("点击链接,下载文件"));
                    textArea.setAttribute("id","detailFileLink");
                    var fileNameStringList = data["data"]["fileName"].split("/");
                    textArea.setAttribute("showName",fileNameStringList[fileNameStringList.length-1])
                    textArea.setAttribute("href",data["title"]+"/"+id + "/file");
                }else{
                    textArea = document.createElement("input");
                    textArea.setAttribute("type","file");
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
    $("#detailFileLink").click(function(){
        download(this.getAttribute("href"),this.getAttribute("showName"));
        return false;
    })
}
function download(url,downloadReName) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);        // 也可以使用POST方式，根据接口
    xhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("token"));
    xhr.responseType = "blob";    // 返回类型blob
    // 定义请求完成的处理函数，请求前也可以增加加载框/禁用下载按钮逻辑
    xhr.onload = function () {
        // 请求完成
        if (this.status === 200) {
            // 返回200
            var blob = this.response;
            var reader = new FileReader();
            reader.readAsDataURL(blob);    // 转换为base64，可以直接放入a表情href
            reader.onload = function (e) {
                // 转换完成，创建一个a标签用于下载
                var a = document.createElement('a');
                a.download = downloadReName;
                a.href = e.target.result;
                $("body").append(a);    // 修复firefox中无法触发click
                a.click();
                $(a).remove();
            }
        }else if(this.status === 403){       
            reLogin();
        }
    };
    // 发送ajax请求
    xhr.send()
}

function base64decode(str) {
    var base64DecodeChars = new Array(
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
        -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
        -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
        41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);
    var c1, c2, c3, c4;
    var i, len, out;
          len = str.length;
          i = 0;
          out = "";
          while(i < len) {
          
          do {
              c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
          } while(i < len && c1 == -1);
          if(c1 == -1)
              break;
          
          do {
              c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
          } while(i < len && c2 == -1);
          if(c2 == -1)
              break;
          out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
          
          do {
              c3 = str.charCodeAt(i++) & 0xff;
              if(c3 == 61)
              return out;
              c3 = base64DecodeChars[c3];
          } while(i < len && c3 == -1);
          if(c3 == -1)
              break;
          out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
          
          do {
              c4 = str.charCodeAt(i++) & 0xff;
              if(c4 == 61)
              return out;
              c4 = base64DecodeChars[c4];
          } while(i < len && c4 == -1);
          if(c4 == -1)
              break;
          out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
          }
          return out;
      }

