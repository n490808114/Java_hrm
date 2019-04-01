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
function setTable(json){
    var data;
    if(json instanceof String){
        data = JSON.parse(json);
    }else{
        data = json;
    }
    var button = document.getElementById("main-btn");
    var table = document.createElement("table");
    button.parentNode.insertBefore(table,button);
    table.setAttribute("class","main-table");
    var titleSet=false;
    for(var childData in data){
        if (!titleSet){
            var trth = document.createElement("tr");
            table.appendChild(trth);
            for(var childTitle in data[childData]){
                var th = document.createElement("th");
                trth.appendChild(th);
                var thText = document.createTextNode(data[childData][childTitle]);
                th.appendChild(thText);
            }
            titleSetount = true;
            continue;
        }
        var trtd = document.createElement("tr");
        table.appendChild(trtd);
        for(var tdData in data[childData]){
            var td = document.createElement("td");
            trtd.appendChild(td);
            var tdText = document.createTextNode(data[childData][tdData]);
            trtd.appendChild(tdText);
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
                setTable(data);
            },
            error:function(){
                alert("获取数据失败");
            }
        }
    )
    return false;
}
function addForm(data){
    if(data instanceof String){
        data = JSON.parse(data);
    }
    var oldForm = document.getElementById("form");
    if (oldForm != null){
        oldForm.parentNode.removeChild(oldForm);
    }
    var form = document.createElement("form");
    form.setAttribute("id","form");
    var main = document.getElementsByTagName("main")[0];
    main.appendChild(form);
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
}
