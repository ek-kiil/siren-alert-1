// ---------- Calls to the API ----------

async function getSirens() {
    const response = await fetch("/sirens");
    return await response.json();
}

async function getActiveFires() {
    const response = await fetch("/fires?status=active");
    return await response.json();
}

async function postFire(fire) {
    await fetch("/fires", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(fire)
    });
}

async function putClosure(fireId) {
    await fetch("/fires/" + fireId + "/closure", { method: "PUT" });
}

async function postSiren(siren) {
    await fetch("/sirens", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(siren)
    });
}

async function putSiren(sirenId, siren) {
    await fetch("/sirens/" + sirenId, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(siren)
    });
}

async function deleteSiren(sirenId) {
    await fetch("/sirens/" + sirenId, { method: "DELETE" });
}


// ---------- Render functions ----------

function renderFires(fires) {
    const rows = document.getElementById("fireRows");
    rows.innerHTML = "";

    for (const fire of fires) {
        const row = document.createElement("tr");
        addCell(row, fire.latitude);
        addCell(row, fire.longitude);
        addCell(row, new Date(fire.time).toLocaleString("da-DK"));
        addCell(row, fire.closed ? "Ja" : "Nej");
        addCell(row, fire.sirens.length);
        addButton(row, "Luk brand", () => closeFire(fire.id));
        rows.appendChild(row);
    }
}

function renderSirens(sirens) {
    const rows = document.getElementById("sirenRows");
    rows.innerHTML = "";

    for (const siren of sirens) {
        const row = document.createElement("tr");
        addCell(row, siren.name);
        addCell(row, siren.latitude);
        addCell(row, siren.longitude);
        addCell(row, siren.status);
        addCell(row, siren.disabled ? "Ja" : "Nej");
        addButton(row, "Rediger", () => editSiren(siren));
        addButton(row, "Slet", () => removeSiren(siren.id));
        rows.appendChild(row);
    }
}

function addCell(row, text) {
    const cell = document.createElement("td");
    cell.textContent = text;
    row.appendChild(cell);
}

function addButton(row, text, onClick) {
    const cell = document.createElement("td");
    const button = document.createElement("button");
    button.textContent = text;
    button.addEventListener("click", onClick);
    cell.appendChild(button);
    row.appendChild(cell);
}


// ---------- Event handlers ----------

async function reportFire() {
    const fire = {
        latitude: Number(document.getElementById("fireLatitude").value),
        longitude: Number(document.getElementById("fireLongitude").value)
    };

    await postFire(fire);
    await showEverything();
}

async function closeFire(fireId) {
    await putClosure(fireId);
    await showEverything();
}

async function saveSiren() {
    const sirenId = document.getElementById("sirenId").value;
    const siren = {
        name: document.getElementById("sirenName").value,
        latitude: Number(document.getElementById("sirenLatitude").value),
        longitude: Number(document.getElementById("sirenLongitude").value),
        disabled: document.getElementById("sirenDisabled").checked
    };

    // The hidden field has an id when an existing siren is being edited
    if (sirenId) {
        await putSiren(sirenId, siren);
    } else {
        await postSiren(siren);
    }

    clearSirenForm();
    await showEverything();
}

function editSiren(siren) {
    document.getElementById("sirenId").value = siren.id;
    document.getElementById("sirenName").value = siren.name;
    document.getElementById("sirenLatitude").value = siren.latitude;
    document.getElementById("sirenLongitude").value = siren.longitude;
    document.getElementById("sirenDisabled").checked = siren.disabled;
}

async function removeSiren(sirenId) {
    await deleteSiren(sirenId);
    await showEverything();
}

function clearSirenForm() {
    document.getElementById("sirenId").value = "";
    document.getElementById("sirenName").value = "";
    document.getElementById("sirenLatitude").value = "";
    document.getElementById("sirenLongitude").value = "";
    document.getElementById("sirenDisabled").checked = false;
}

async function showEverything() {
    renderFires(await getActiveFires());
    renderSirens(await getSirens());
}

document.getElementById("reportFireButton").addEventListener("click", reportFire);
document.getElementById("saveSirenButton").addEventListener("click", saveSiren);

showEverything();
