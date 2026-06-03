<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.modelo.Usuario" %>
<%
    Usuario u = (Usuario) request.getAttribute("usuario");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Usuario - Inmobiliaria</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:'Segoe UI',Arial,sans-serif; min-height:100vh; display:flex; }
        .panel-form { flex:0 0 42%; background:#fff; padding:48px 40px; display:flex; flex-direction:column; justify-content:center; }
        .panel-deco { flex:1; position:relative; overflow:hidden; }
        .panel-deco img { width:100%; height:100%; object-fit:cover; display:block; }
        .panel-overlay { position:absolute; inset:0; background:rgba(30,27,75,0.45); display:flex; align-items:center; justify-content:center; text-align:center; }
        .panel-overlay h2 { color:white; font-size:22px; }
        .logo { display:flex; align-items:center; gap:8px; margin-bottom:32px; }
        .logo-icon { width:32px; height:32px; background:#6D28D9; border-radius:6px; display:flex; align-items:center; justify-content:center; }
        .logo span { font-size:20px; font-weight:600; color:#1a1a1a; }
        h1 { font-size:24px; font-weight:600; color:#1a1a1a; margin-bottom:24px; }
        .form-group { margin-bottom:14px; }
        label { font-size:12px; color:#666; display:block; margin-bottom:4px; }
        label span { color:#6D28D9; }
        input, select { width:100%; padding:10px 12px; border:1px solid #d1d5db; border-radius:8px; font-size:14px; }
        input:focus, select:focus { outline:none; border-color:#6D28D9; }
        .btn { width:100%; padding:12px; background:#6D28D9; color:white; border:none; border-radius:8px; font-size:15px; font-weight:600; cursor:pointer; margin-top:8px; }
        .btn:hover { background:#5b21b6; }
        .link { display:block; text-align:center; margin-top:14px; font-size:13px; color:#6D28D9; text-decoration:none; }
    </style>
</head>
<body>
    <div class="panel-form">
        <div class="logo">
            <div class="logo-icon">
                <svg width="18" height="18" viewBox="0 0 20 20" fill="none">
                    <path d="M10 2L2 9h2v9h5v-5h2v5h5V9h2L10 2z" fill="white"/>
                </svg>
            </div>
            <span>Inmobiliaria</span>
        </div>
        <h1>Editar usuario</h1>

        <form action="UsuarioServlet" method="POST">
            <input type="hidden" name="accion" value="actualizar">
            <input type="hidden" name="id" value="<%= u.getId() %>">

            <div class="form-group">
                <label>Nombre <span>*</span></label>
                <input type="text" name="nombre" value="<%= u.getNombre() %>" required>
            </div>
            <div class="form-group">
                <label>Apellido <span>*</span></label>
                <input type="text" name="apellido" value="<%= u.getApellido() %>" required>
            </div>
            <div class="form-group">
                <label>Correo electrónico <span>*</span></label>
                <input type="email" name="email" value="<%= u.getEmail() %>" required>
            </div>
            <div class="form-group">
                <label>Teléfono</label>
                <input type="text" name="telefono" value="<%= u.getTelefono() %>">
            </div>
            <div class="form-group">
                <label>Contraseña</label>
                <input type="password" name="contrasena" value="<%= u.getContrasena() %>">
            </div>
            <div class="form-group">
                <label>Tipo de usuario</label>
                <select name="tipo_usuario">
                    <option value="comprador"     <%= "comprador".equals(u.getTipoUsuario())     ? "selected" : "" %>>Comprador</option>
                    <option value="vendedor"      <%= "vendedor".equals(u.getTipoUsuario())      ? "selected" : "" %>>Vendedor</option>
                    <option value="agente"        <%= "agente".equals(u.getTipoUsuario())        ? "selected" : "" %>>Agente</option>
                    <option value="administrador" <%= "administrador".equals(u.getTipoUsuario()) ? "selected" : "" %>>Administrador</option>
                </select>
            </div>
            <button class="btn" type="submit">Guardar cambios</button>
        </form>
        <a class="link" href="UsuarioServlet?accion=listar">&larr; Volver a la lista</a>
    </div>

    <div class="panel-deco">
        <img src="images/landscape-mountain.jpg" alt="Paisaje"
             onerror="this.style.display='none';this.parentElement.style.background='linear-gradient(135deg,#1e1b4b,#4c1d95)'">
        <div class="panel-overlay">
            <h2>Actualiza tus datos</h2>
        </div>
    </div>
</body>
</html>