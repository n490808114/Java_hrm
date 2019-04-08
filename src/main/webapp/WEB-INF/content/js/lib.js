function addLoadEvent(func){
    var oldonload=window.onload;
    if(typeof window.onload != "function"){
        window.onload=func;
    }else{
        window.onload = function(){
            oldonload();
            func();
        }
    }
}
function insertAfter(newNode,targetNode){
    var parent = targetNode.parentNode;
    if(targetNode == parent.lastNode){
        parent.appendChild(newNode);
    }else{
        parent.insertBefore(newNode,targetNode.nextSibing);
    }
}
function getHTTPObject(){
    if(typeof XMLHttpRequest == "undefined"){
        try{ return new ActiveXObject("Msxml2.XMLHTTP.6.0");}catch(e){}
        try{ return new ActiveXObject("Msxml2.XMLHTTP.3.0");}catch(e){}
        try{ return new ActiveXObject("Msxml2.XMLHTTP");}catch(e){}
        return false;
    }
    return new XMLHttpRequest();
}
function displayAbbreviations(){
    if(!document.getElementsByTagName)return false;
    if(!document.createElement)return false;
    if(!document.createTextNode)return false;
    var abbreviations = document.getElementsByTagName("abbr");
    if(abbreviations.length<1)return false;
    var defs = new Array();
    for(var i=0;i<abbreviations.length;i++){
        if(abbreviations[i].childNodes.length<1)continue;
        var definition = abbreviations[i].getAttribute("title");
        var key = abbreviations[i].lastChild.nodeValue;
        defs[key] = definition;
    }
    var dl = document.createElement("dl");
    document.body.appendChild(dl);
    for(each in defs){
        var dt = document.createElement("dt");
        var dd = document.createElement("dd");
        var dtValue = document.createTextNode(each);
        var ddValue = document.createTextNode(defs[each]);
        dt.appendChild(dtValue);
        dd.appendChild(ddValue);
        dl.appendChild(dt);
        dl.appendChild(dd);
    }
    if(dl.childNodes.length<1)return false;
}
function displayCitations(){
    if(!document.getElementsByTagName)return false;
    if(!document.createElement)return false;
    if(!document.createTextNode)return false;
    var blockquotes = document.getElementsByTagName("blockquote");
    for(var i=0;i<blockquotes.length;i++){
        eachQuote = blockquotes[i];
        if(!eachQuote.getAttribute("cite"))continue;
        var link = eachQuote.getAttribute("cite");
        var nodes = eachQuote.getElementsByTagName("*");
        if(nodes.length<1)continue;
        var lastNode = nodes[nodes.length-1];
        var a = document.createElement("a");
        a.setAttribute("href",link);
        var text = document.createTextNode("source...");
        a.appendChild(text);
        var superscript = document.createElement("sup");
        superscript.appendChild(a);
        lastNode.appendChild(superscript);
    }
}
function displayAccessKey(){
    if(!document.getElementsByTagName)return false;
    if(!document.createEvent)return false;
    if(!document.createTextNode) return false;
    var as = document.getElementsByTagName("a");
    var keys = new Array();
    for(var i=0;i<as.length;i++){
        var a = as[i];
        if(!a.getAttribute("accesskey"))continue;
        var key = a.getAttribute("accesskey");
        var link = a.getAttribute("href");
        keys[key] = link;
    }
    var ul = document.createElement("ul");
    document.getElementsByTagName("body")[0].appendChild(ul);
    for(eachA in keys){
        var li = document.createElement("li");
        var p = document.createElement("p");
        var pValue = document.createTextNode("快捷键+"+eachA+" ");
        var aValue = document.createTextNode(keys[eachA]);
        var a = document.createElement("a");
        a.appendChild(aValue);
        a.setAttribute("href",keys[eachA]);
        p.appendChild(pValue);
        p.appendChild(a);
        li.appendChild(p);
        ul.appendChild(li);
    }
}
function getNextElement(node){
    if(node.nodeType == 1){
        return node;
    }
    if(node.nextSibling){
        return getNextElement(node.nextSibling);
    }
    return null;
}
function styleHeaderSiblings(){
    if(!document.getElementsByTagName)return false;
    var headers = document.getElementsByTagName("h1");
    var elem;
    for(var i=0;i<headers.length;i++){
        elem = getNextElement(headers[i].nextSibling);
        elem.style.fontFamily = "Bold";
        elem.style.fontSize = "1.2em";
    }
}
function stripeTables(){
    if(!document.getElementsByTagName)return false;
    var tables = document.getElementsByTagName("table");
    for(var i=0;i<tables.length;i++){
        var table = tables[i];
        var trs = table.getElementsByTagName("tr");
        var odd = false;
        for(var a=0;a<trs.length;a++){
            if(odd == false){
                trs[a].style.backgroundColor="#ffc";
                odd = true;
            }else{odd = false;}
        }
    }
}
function setMainTable(json){
    var data;
    if(json instanceof String){
        data = JSON.parse(json);
    }else{
        data = json;
    }
    var list = getTableWidthList();
    var button = document.getElementById("main-btn");
    var table = document.getElementById("main_table");
    if(table != null){
        table.remove();
    }
    table = document.createElement("table");
    button.parentNode.insertBefore(table,button);
    table.setAttribute("id","main_table");
    var titleSet=false;
    for(var childData in data){
        if (!titleSet){
            var trth = document.createElement("tr");
            table.appendChild(trth);
            var a = 0;
            for(var childTitle in data[childData]){
                var th = document.createElement("th");
                trth.appendChild(th);
                th.setAttribute("name",childTitle);
                var thText = document.createTextNode(data[childData][childTitle]);
                th.appendChild(thText);

                th.style.width=list[a];
                a ++;
            }
            titleSet = true;
            continue;
        }
        var trtd = document.createElement("tr");
        table.appendChild(trtd);
        for(var tdData in data[childData]){
            var td = document.createElement("td");
            trtd.appendChild(td);
            var tdText = document.createTextNode(data[childData][tdData]);
            td.appendChild(tdText);
            td.setAttribute("title",data[childData][tdData])
            td.setAttribute("name",tdData);
            td.setAttribute("onclick","openDetail(getTdDetail(this))");
        }
    }
}
function asideHref(point){
    var title = document.getElementById("main_title");
    title.firstChild.nodeValue = point.firstChild.nodeValue;
    title.setAttribute("name",point.getAttribute("href"));
    var ajaxUrl = point.getAttribute("href");
    $.ajax(
        {
            url:ajaxUrl,
            type:"get",
            asyns:false,
            success:function(data){
                setMainTable(data);
            },
            error:function(){
                alert("获取数据失败");
            }
        }
    )
    return false;
}
function addCreateForm(data){
    if(data instanceof String){
        data = JSON.parse(data);
    }
    addCoverDiv();
    var oldForm = document.getElementById("form");
    var name = document.getElementById("main_title").getAttribute("name");
    if (oldForm != null){
        if(oldForm.getAttribute("name") == name){
            oldForm.style.removeProperty("display");
            insertAfter(oldForm,document.getElementById("divCover"));
            return false;
        }else{
            oldForm.remove();
        }
    }
    var form = document.createElement("form");
    var title = document.getElementById("main_title").getAttribute("name");
    form.setAttribute("id","form");
    form.setAttribute("name",title);
    form.setAttribute("method","post");
    form.setAttribute("action",title+"Creator");
    form.setAttribute("onsubmit","return submitCreate(this)");
    var body = document.getElementsByTagName("body")[0];

    body.appendChild(form);
    var fieldset = document.createElement("fieldset");
    form.appendChild(fieldset);
    for(var x in data){
        var p = document.createElement("p");
        var input = document.createElement("input");
        if(x == "content"){
            input = document.createElement("textarea");
        }else if(x == "email"){
            input.setAttribute("type","email");
        }else if(x == "password"){
            input.setAttribute("type","password");
        }else{
            input.setAttribute("type","text");
        }
        fieldset.appendChild(p);
        p.appendChild(input);
        input.setAttribute("placeholder",data[x])
        input.setAttribute("name",x);
    }
    var div = document.createElement("div");
    form.appendChild(div);
    div.setAttribute("id","form_btn1");
    var submit = document.createElement("input");
    div.appendChild(submit);
    submit.setAttribute("type","submit");
    submit.setAttribute("value","提交");
    return true;
}
function submitCreate(point){
    $("#from").ajaxSubmit(function(message){
        if(message == "true"){
            alert("公告创建成功");
        }else if(message == "false"){
            alert("公告创建失败");
        }
    })
    return false;
}

function openCreator(){
    var ajaxUrl = document.getElementById("main_title").getAttribute("name");
    $.ajax({
        url:ajaxUrl+"Create",
        type:"get",
        asyns:false,
        success:function(data){
            addCreateForm(data);
        },
        error:function(){
            alert("数据获取失败");
        }
    })
}

function getTableWidthList(){
    var name = document.getElementById("main_title").getAttribute("name");
    if(name == "notice"){
        return ["5%","10%","65%","10%","10%"];
    }
}
function getTableTitleList(){
    var table = document.getElementById("main_table");
    var thtr = table.firstChild;
    var map = new Array;
    for(var a=0;a<thtr.childNodes.length;a++){
        map[thtr.childNodes[a].getAttribute("name")]=thtr.childNodes[a].firstChild.nodeValue;
    }
    return map;
}
function getTdDetail(point){
    var childNodes = point.parentNode.childNodes;
    var map = new Array;
    for(var a=0;a<childNodes.length;a++){
        map[childNodes[a].getAttribute("name")] = childNodes[a].firstChild.nodeValue;
    }
    return map;
}
function openDetail(map){
    var oldDetail = document.getElementById("detail");
    if(oldDetail != null){
        oldDetail.remove();
    }
    addCoverDiv();
    var body = document.getElementsByTagName("body")[0];
    var div = document.createElement("div");
    body.appendChild(div);
    div.setAttribute("id","detail");

    var titleList = getTableTitleList();
    for(var b in map){
        var label = document.createElement("label");
        label.setAttribute("name",b);
        var text = document.createTextNode(titleList[b]+":");
        label.appendChild(text);

        var detailP = document.createElement("p");
        var detailText = document.createTextNode(map[b]);
        detailP.appendChild(detailText);
        if((b != "id") &&( b != "createDate") && (b != "user")){
            detailP.setAttribute("contenteditable","true");
        }else{
            detailP.setAttribute("title","不能修改这项内容");
        }

        var p = document.createElement("p");
        p.appendChild(label);
        p.appendChild(detailP);
        div.appendChild(p);
    }
    var buttonDiv = document.createElement("div");
    var button = document.createElement("button");
    button.setAttribute("onclick","changeDetail(this)");
    button.setAttribute("type","button");
    button.setAttribute("value","提交更改");
    var buttonText = document.createTextNode("提交更改");
    button.appendChild(buttonText);
    buttonDiv.appendChild(button);
    div.appendChild(buttonDiv);
}
function addCoverDiv(){
    var body = document.getElementsByTagName("body")[0];

    var divCover = document.createElement("div");
    body.appendChild(divCover);
    divCover.setAttribute("id","divCover");
    divCover.setAttribute("onclick","closePopUp()");
}
function closePopUp(){
    var detail = document.getElementById("detail");
    var form = document.getElementById("form");
    if(detail != null){detail.remove()}
    if(form != null){form.style.display = "none";}
    while(true){
        var divCover = document.getElementById("divCover");
        if(divCover == null){break};
        divCover.remove();
    }

}

