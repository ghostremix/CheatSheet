<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.library.repository.*, com.library.model.*, com.library.config.DBConnect, java.util.List"%>
<%
if (session.getAttribute("userObj") == null) {
	response.sendRedirect("login.jsp");
	return;
}

TitleRepo titleRepo = new TitleRepo(DBConnect.getConnection());
CategoryRepo catRepo = new CategoryRepo(DBConnect.getConnection());
CheatSheetRepo sheetRepo = new CheatSheetRepo(DBConnect.getConnection());

List<Title> allTitles = titleRepo.getAllTitles();
List<Category> allCategories = catRepo.getAllCategories();
List<CheatSheet> sheets = sheetRepo.getAllCheatsheets();

boolean isAdmin = "admin".equals(session.getAttribute("role"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>DevHub | Dashboard</title>
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.4.2/css/all.min.css">
<link rel="stylesheet" href="dashboard.css">
</head>
<body>
	<header class="main-header">
		<div class="header-content">
			<div class="brand" onclick="location.reload()">
				<i class="fas fa-bolt"></i> <span>DevHub</span>
			</div>

			<div class="search-container">
				<div class="search-box">
					<i class="fas fa-search search-icon"></i> <input type="text"
						id="globalSearch"
						placeholder="Search for cheatsheet, tags, categories..."
						autocomplete="off">
				</div>
				<div id="searchDropdown" class="search-dropdown hidden"></div>
			</div>

			<div class="header-right">
				<%
				if (isAdmin) {
				%>
				<div class="mode-switch-container">
					<span class="mode-label">Edit Mode</span> <label class="switch">
						<input type="checkbox" id="editModeToggle"
						onchange="toggleEditMode()"> <span class="slider round"></span>
					</label>
				</div>
				<button class="add-btn" onclick="addNewTitle()"
					title="Add New Title">
					<i class="fas fa-plus"></i>
				</button>
				<%
				}
				%>
				<a href="javascript:void(0)" onclick="showSavedView()"
					class="logout-link" title="Saved Snippets"
					style="margin-right: 15px;"> <i class="fas fa-bookmark"></i>
				</a> <a href="logout.jsp" style="text-decoration: none;">
					<button class="Btn" title="Logout">
						<div class="sign">
							<div class="sign logout-icon"></div>
							<div class="text">Logout</div>
						</div>
						<div class="text">Logout</div>
					</button>
				</a>
			</div>
		</div>
	</header>

	<div class="main-container">
		<div id="homeView">
			<%
			for (Title t : allTitles) {
			%>
			<section class="title-section">
				<div class="title-header">
					<h2><%=t.getTitle_name()%></h2>
					<%
					if (isAdmin) {
					%>
					<div class="admin-actions hidden">
						<button class="action-btn add"
							onclick="addCategoryToTitle(<%=t.getTitle_id()%>, '<%=t.getTitle_name()%>')">
							<i class="fas fa-plus"></i>
						</button>
						<button class="action-btn edit"
							onclick="editTitle(<%=t.getTitle_id()%>, '<%=t.getTitle_name()%>')">
							<i class="fas fa-pen"></i>
						</button>
						<button class="action-btn delete text-red"
							onclick="deleteTitle(<%=t.getTitle_id()%>)">
							<i class="fas fa-trash"></i>
						</button>
					</div>
					<%
					}
					%>
				</div>

				<div class="pill-grid">
					<%
					for (Category cat : allCategories) {
						if (cat.getTitleId() == t.getTitle_id()) {
					%>
					<div class="cat-pill color-blue"
						onclick="showSnippets('<%=cat.getCategoryId()%>', '<%=cat.getCategoryName()%>')">
						<div class="pill-info">
							<i class="fas fa-code"></i> <span><%=cat.getCategoryName()%></span>
						</div>
						<%
						if (isAdmin) {
						%>
						<div class="admin-actions hidden"
							onclick="event.stopPropagation()">
							<button class="action-btn edit"
								onclick="editCategory(<%=cat.getCategoryId()%>, '<%=cat.getCategoryName()%>')">
								<i class="fas fa-edit"></i>
							</button>
							<button class="action-btn delete"
								onclick="deleteCategory(<%=cat.getCategoryId()%>)">
								<i class="fas fa-times"></i>
							</button>
						</div>
						<%
						}
						%>
					</div>
					<%
					}
					}
					%>
				</div>
			</section>
			<%
			}
			%>
		</div>

		<div id="savedView" class="hidden">
			<div class="view-header">
				<button onclick="showHome()" class="back-btn">
					<i class="fas fa-arrow-left"></i> Back
				</button>
				<h2>My Saved Snippets</h2>
			</div>
			<div id="savedSnippetsContainer" class="snippets-grid"></div>
		</div>
	</div>

	<div id="snippetsView" class="hidden">
		<div class="view-header">
			<button onclick="showHome()" class="back-btn">
				<i class="fas fa-arrow-left"></i> Back
			</button>
			<h2 id="selectedCatName">Category Name</h2>
			<%
			if (isAdmin) {
			%>
			<button onclick="addSnippet()" class="add-snippet-btn">
				<i class="fas fa-plus"></i> Add Snippet
			</button>
			<%
			}
			%>
		</div>
		<div id="snippetsContainer" class="snippets-grid"></div>
	</div>

	<script>
    const allSheets = [
        <%for (CheatSheet s : sheets) {%>
        { 
            id: <%=s.getSheetId()%>, 
            catId: "<%=s.getCategory().getCategoryId()%>", 
            title: "<%=s.getTitle().replace("\"", "\\\"")%>", 
            content: `<%=s.getContent().replace("`", "\\`").replace("$", "\\$")%>` 
        },
        <%}%>
    ];

    let currentCatId = null;

    document.addEventListener("DOMContentLoaded", function() {
        const editModeToggle = document.getElementById('editModeToggle');
        const savedEditMode = localStorage.getItem('editMode') === 'true';
        if (editModeToggle) {
            editModeToggle.checked = savedEditMode;
            applyEditMode(savedEditMode);
        }
    });
    
    function escapeHTML(str) {
        return str.replace(/[&<>"']/g, function(m) {
            return {
                '&': '&amp;',
                '<': '&lt;',
                '>': '&gt;',
                '"': '&quot;',
                "'": '&#39;'
            }[m];
        });
    }

    function toggleEditMode() {
        const isEdit = document.getElementById('editModeToggle').checked;
        localStorage.setItem('editMode', isEdit);
        applyEditMode(isEdit);
    }

    function applyEditMode(isEdit) {
        document.querySelectorAll('.admin-actions').forEach(el => {
            isEdit ? el.classList.remove('hidden') : el.classList.add('hidden');
        });
        if (!document.getElementById('snippetsView').classList.contains('hidden')) {
            renderSnippets(currentCatId);
        }
    }

    function addNewTitle() {
        let name = prompt("Enter New Section Title:");
        if(name && name.trim() !== "") window.location.href = "AddTitle?titleName=" + encodeURIComponent(name);
    }

    function addCategoryToTitle(tId, titleName) {
        let name = prompt("Enter New Category for " + titleName + ":");
        if(name && name.trim() !== "") window.location.href = "AddCategory?name=" + encodeURIComponent(name) + "&titleId=" + tId;
    }

    function showSnippets(id, name) {
        currentCatId = String(id); // ID ကို String အဖြစ် သေချာပြောင်းလဲ assign လုပ်ပါသည်
        document.getElementById('homeView').classList.add('hidden');
        document.getElementById('savedView').classList.add('hidden');
        document.getElementById('snippetsView').classList.remove('hidden');
        document.getElementById('selectedCatName').innerText = name;
        renderSnippets(currentCatId);
    }

    function addSnippet() {
        if(currentCatId) {
            window.location.href = "add_snippet.jsp?catId=" + currentCatId;
        } else {
            alert("Category selection error!");
        }
    }

    function renderSnippets(id) {
        const container = document.getElementById('snippetsContainer');
        // Type ကွဲလွဲမှု မရှိစေရန် String() အချင်းချင်း တိုက်စစ်သည်
        const filtered = allSheets.filter(s => String(s.catId) === String(id));
        const toggleBtn = document.getElementById('editModeToggle');
        const isEditMode = toggleBtn ? toggleBtn.checked : false;
        
        if (filtered.length === 0) {
            container.innerHTML = '<p class="empty">No snippets found in this category.</p>';
            return;
        }

        container.innerHTML = filtered.map(s => `
            <div class="snippet-card">
                <div class="snippet-top">
                    <h4>\${escapeHTML(s.title)}</h4>
                    <div class="snippet-btns">
                        <button onclick="toggleFavorite(\${s.id}, this)" class="icon-btn" title="Save Snippet">
                            <i class="far fa-bookmark"></i>
                        </button>
                        <button onclick="copyCode(this)" class="icon-btn" title="Copy Code">
                            <i class="far fa-copy"></i>
                        </button>
                        \${isEditMode ? `
                        <div class="admin-actions">
                            <button class="action-btn edit" onclick="window.location.href='edit_snippet.jsp?id=\${s.id}'">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="action-btn delete text-red" onclick="deleteSnippet(\${s.id})">
                                <i class="fas fa-trash"></i>
                            </button>
                        </div>
                        ` : ''}
                    </div>
                </div>
                <pre><code>\${escapeHTML(s.content)}</code></pre>
            </div>
        `).join('');
    }

    // showHome ပြင်ဆင်ထားသည်
    function showHome() {
        document.getElementById('homeView').classList.remove('hidden');
        document.getElementById('snippetsView').classList.add('hidden');
        document.getElementById('savedView').classList.add('hidden');
        currentCatId = null;
    }
    
    function toggleFavorite(id, btn) {
        const icon = btn.querySelector('i');
        const isSaving = icon.classList.contains('far');

        fetch('FavoriteServlet?id=' + id + '&action=' + (isSaving ? 'add' : 'remove'))
            .then(res => res.json())
            .then(data => {
                if(data.success) {
                    icon.classList.toggle('far');
                    icon.classList.toggle('fas');
                    icon.style.color = isSaving ? '#10b981' : '';
                    
                    // Saved page ကြီးထဲ ရောက်နေတုန်း Unsave လုပ်လိုက်ရင် card ကို UI ကနေ တန်းဖျောက်ဖို့
                    if(!document.getElementById('savedView').classList.contains('hidden') && !isSaving) {
                        btn.closest('.snippet-card').remove();
                    }
                }
            })
            .catch(err => console.error("Error toggling favorite:", err));
    } 
    
    document.addEventListener("DOMContentLoaded", function() {
        const searchInput = document.getElementById('globalSearch');
        const dropdown = document.getElementById('searchDropdown');

        searchInput.addEventListener('input', function() {
            let val = this.value.trim();
            if (val.length < 1) {
                dropdown.innerHTML = '';
                dropdown.classList.add('hidden');
                return;
            }

            fetch('SearchServlet?query=' + encodeURIComponent(val))
                .then(res => res.json())
                .then(data => {
                    if (data.length === 0) {
                        dropdown.innerHTML = '<div class="search-item"><span style="color:#9ca3af;">No results found</span></div>';
                        dropdown.classList.remove('hidden');
                        return;
                    }

                    // နှိပ်လိုက်ရင် String အဖြစ် တန်းရောက်သွားအောင် လုပ်ဆောင်ထားသည်
                    dropdown.innerHTML = data.map(item => `
  				  <div class="search-item" onclick="selectSearchItem(&apos;\${item.catId}&apos;, &apos;\${escapeHtmlJS(item.catName)}&apos;)">
     			   <span class="item-title">\${escapeHTML(item.title)}</span>
     			   <span class="item-badge">\${escapeHTML(item.catName)}</span>
   					 </div>
				`).join('');
                    dropdown.classList.remove('hidden');
                });
        });

        document.addEventListener('click', function(e) {
            if (!searchInput.contains(e.target) && !dropdown.contains(e.target)) {
                dropdown.classList.add('hidden');
            }
        });
    });

    // Dropdown ထဲက တစ်ခုခုကို နှိပ်လိုက်ရင် သက်ဆိုင်ရာ Category Snippet ဆီသို့ အလိုအလျောက် သွားပေးမည်
    function selectSearchItem(catId, catName) {
        document.getElementById('globalSearch').value = '';
        document.getElementById('searchDropdown').classList.add('hidden');
        showSnippets(String(catId), catName); // String Cast သေချာလုပ်ပြီး လှမ်းခေါ်လိုက်ပါပြီ
    }

    // ဖြည့်စွက် အကူအညီ function
    function escapeHtmlJS(str) {
        return str.replace(/'/g, "\\'").replace(/"/g, '\\"');
    }

    function copyCode(btn) {
        const code = btn.closest('.snippet-card').querySelector('code').innerText;
        navigator.clipboard.writeText(code);
        btn.innerHTML = '<i class="fas fa-check"></i>';
        setTimeout(() => btn.innerHTML = '<i class="far fa-copy"></i>', 2000);
    }
    
    // showSavedView ပြင်ဆင်ထားသည် (s.sheetId သုံးထားသည်)
    function showSavedView() {
        document.getElementById('homeView').classList.add('hidden');
        document.getElementById('snippetsView').classList.add('hidden');
        document.getElementById('savedView').classList.remove('hidden');

        const container = document.getElementById('savedSnippetsContainer');
        container.innerHTML = '<p class="empty">Loading saved snippets...</p>';
        
        fetch('GetSavedSnippetsServlet')
            .then(res => res.json())
            .then(savedList => {
                if(!savedList || savedList.length === 0) {
                    container.innerHTML = '<p class="empty">You haven\'t saved any snippets yet.</p>';
                    return;
                }
                container.innerHTML = savedList.map(s => `
                    <div class="snippet-card">
                        <div class="snippet-top">
                            <h4>\${escapeHTML(s.title)}</h4>
                            <div class="snippet-btns">
                                 <button onclick="toggleFavorite(\${s.sheetId}, this)" class="icon-btn">
                                    <i class="fas fa-bookmark" style="color: #10b981;"></i>
                                </button>
                                <button onclick="copyCode(this)" class="icon-btn"><i class="far fa-copy"></i></button>
                            </div>
                        </div>
                        <pre><code>\${escapeHTML(s.content)}</code></pre>
                    </div>
                `).join('');
            })
            .catch(err => {
                console.error(err);
                container.innerHTML = '<p class="empty">Error loading data.</p>';
            });
    }
    
 // --- Edit & Delete Functions ---
 function editTitle(id, oldName) {
     let newName = prompt("Edit Title Name:", oldName);
     if (newName && newName.trim() !== "" && newName !== oldName) {
         window.location.href = "UpdateTitle?id=" + id + "&name=" + encodeURIComponent(newName);
     }
 }
 function deleteTitle(id) {
     if (confirm("Are you sure you want to delete this Title and all its categories?")) {
         window.location.href = "DeleteTitle?id=" + id;
     }
 }
 function editCategory(id, oldName) {
     let newName = prompt("Edit Category Name:", oldName);
     if (newName && newName.trim() !== "" && newName !== oldName) {
         window.location.href = "UpdateCategory?id=" + id + "&name=" + encodeURIComponent(newName);
     }
 }
 function deleteCategory(id) {
     if (confirm("Are you sure you want to delete this Category?")) {
         window.location.href = "DeleteCategory?id=" + id;
     }
 }
 function deleteSnippet(id) {
     if (confirm("Are you sure you want to delete this Snippet?")) {
         window.location.href = "DeleteSnippet?id=" + id;
     }
 }
</script>
</body>
</html>