<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>Q&A With Database</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="chat-container">
    <div style="position: absolute; top: 15px; right: 20px;">
    <label class="switch">
        <input type="checkbox" id="dark-mode-toggle">
        <span class="slider round"></span>
    </label>
    </div>
    <div class="chat-header">
        <h2>💬 Q&A With Database</h2>
    </div>

    <div id="chat-box" class="chat-box"></div>

    <div class="chat-input-container">
        <input type="text" id="user-input" placeholder="Type your question...">
        <button id="send-btn">Send</button>
    </div>
</div>

<script>
    $(document).ready(function () {
        $("#send-btn").click(function () {
            sendMessage();
        });

        $("#user-input").keypress(function (e) {
            if (e.which === 13) {
                sendMessage();
            }
        });

function sendMessage() {
    let question = $("#user-input").val().trim();
    if (question === "") return;

    // Append user message
    $("#chat-box").append(`<div class='user-msg'>👤 ${question}</div>`);
    $("#user-input").val("");  // Clear input

    // Scroll chat to bottom
    $("#chat-box").scrollTop($("#chat-box")[0].scrollHeight);

    $.ajax({
        url: "/chat/ask",
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({ question: question }),

        success: function (response) {
            console.log("✅ Response from backend:", response);

            let formatted = "";

            if (Array.isArray(response) && response.length > 0 && typeof response[0] === "object") {
                const headers = Object.keys(response[0]);
                let table = "<table class='result-table'><thead><tr>";
                headers.forEach(header => {
                    table += `<th>${header}</th>`;
                });
                table += "</tr></thead><tbody>";

                response.forEach(row => {
                        if (typeof row !== "object") return; // skip malformed rows
                        table += "<tr>";
                        headers.forEach(header => {
                            let cellValue = row[header] !== null && row[header] !== undefined ? row[header] : "";
                            table += `<td>${cellValue}</td>`;
                        });
                        table += "</tr>";
                    });

                table += "</tbody></table>";
                formatted = table;
            } else if (response.error) {
                formatted = `<div class='bot-msg error'>⚠️ ${response.error}<br><small>${response.sql || ''}</small></div>`;
            } else {
                formatted = `<pre>${JSON.stringify(response, null, 2)}</pre>`;
            }

            // Append bot response
            $("#chat-box").append(`<div class='bot-msg'>🤖 ${formatted}</div>`);
            $("#chat-box").scrollTop($("#chat-box")[0].scrollHeight);
        },

        error: function (xhr, status, error) {
            console.error("❌ Error:", error);
            $("#chat-box").append(`<div class='bot-msg error'>⚠️ Error communicating with server.</div>`);
            $("#chat-box").scrollTop($("#chat-box")[0].scrollHeight);
        }
    });
}

    });
</script>
<script>
    // Toggle dark mode
$("#dark-mode-toggle").on("change", function () {
    $("body").toggleClass("dark-mode");
});

</script>
</body>
</html>
