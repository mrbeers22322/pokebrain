const pokemon = [
  { name: "Groudon", detail: "Lucky · Legendary", cp: 3998, iv: "98%", level: "38", tags: ["legendary"] },
  { name: "Tyranitar", detail: "Standard", cp: 4156, iv: "100%", level: "46.5", tags: [] },
  { name: "Charizard", detail: "Buddy", cp: 3024, iv: "98%", level: "43", tags: [] }
];
const grid = document.querySelector("#pokemon-grid");
const search = document.querySelector("#search");
let activeFilter = "all";
function render() {
  const query = search.value.trim().toLowerCase();
  const visible = pokemon.filter(item => {
    const matchesSearch = `${item.name} ${item.detail}`.toLowerCase().includes(query);
    return matchesSearch && (activeFilter === "all" || item.tags.includes(activeFilter));
  });
  grid.innerHTML = visible.length ? visible.map(item => `
    <article class="pokemon-card">
      <div class="pokemon-head"><div><div class="pokemon-name">${item.name}</div><div class="pokemon-sub">${item.detail}</div></div><span class="badge">${item.tags.includes("legendary") ? "Legendary" : "Tracked"}</span></div>
      <div class="pokemon-stats"><div class="pokemon-stat"><strong>${item.cp}</strong><span>CP</span></div><div class="pokemon-stat"><strong>${item.iv}</strong><span>IV</span></div><div class="pokemon-stat"><strong>${item.level}</strong><span>Level</span></div></div>
    </article>`).join("") : `<div class="panel"><strong>No Pokémon match that filter.</strong><p class="pokemon-sub">Try another search or import a collection.</p></div>`;
}
search.addEventListener("input", render);
document.querySelectorAll(".filter").forEach(button => button.addEventListener("click", () => {
  document.querySelectorAll(".filter").forEach(item => item.classList.remove("active"));
  button.classList.add("active"); activeFilter = button.dataset.filter; render();
}));
render();
