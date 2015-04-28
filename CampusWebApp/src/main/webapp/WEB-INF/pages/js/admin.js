

$(function () {
    $("#btnAdd").bind("click", function () {
        var div = $("<div>");
        div.html(GetDynamicTextBox(""));
        $("#TextBoxContainer").append(div);
    });
    $("#btnGet").bind("click", function () {
        var values = "";
        $("pr").each(function () {
            values += $(this).val();
        });
        alert(values);
    });
    $("#TextBoxContainer").on("click", ".remove", function () {
        $(this).closest("div").remove();
    });
     $("#TextBoxContainer").on("click", ".edit", function () {
        $(this).closest("div").remove();
    });
});
function GetDynamicTextBox(value) {
    return ' <div id="pr">aaaaaaabbbbbb'+'<input type="button" value="Remove" class="remove"/>'+'<input type="button" value="Edit" class="edit"/> </div>'
}