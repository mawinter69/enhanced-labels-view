Behaviour.specify(".elv-expand-agents", "elv-title-toggle", 0, function (button) {
    const container = button.closest(".elv-container");
    const tableDiv = container.querySelector(".elv-agent-table");
    const agentListShort = container.querySelector(".elv-agent-list-short");
    button.addEventListener("click", function () {
        tableDiv.classList.toggle("jenkins-hidden");;
        agentListShort.classList.toggle("jenkins-hidden");
        button.classList.toggle("expanded");
    });
});