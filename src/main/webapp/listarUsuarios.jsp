<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.microusuarios.modelo.UsuarioDTO, java.util.List" %>
<%
    List<UsuarioDTO> usuarios = (List<UsuarioDTO>) request.getAttribute("usuarios");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Usuarios - Inmobiliaria</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:'Segoe UI',Arial,sans-serif; background:#f9fafb; padding:32px; }
        .header { display:flex; justify-content:space-between; align-items:center; margin-bottom:24px; }
        h1 { font-size:22px; color:#1a1a1a; }
        table { width:100%; border-collapse:collapse; background:white; border-radius:10px; overflow:hidden; box-shadow:0 1px 4px rgba(0,0,0,0.08); }
        th { background:#6D28D9; color:white; padding:12px 16px; text-align:left; font-size:13px; }
        td { padding:12px 16px; font-size:13px; color:#374151; border-bottom:1px solid #f3f4f6; }
        tr:last-child td { border-bottom:none; }
        tr:hover td { background:#faf5ff; }
        .badge { padding:3px 10px; border-radius:12px; font-size:11px; font-weight:600; }
        .badge-comprador { background:#dbeafe; color:#1e40af; }
        .badge-vendedor { background:#d1fae5; color:#065f46; }
        .badge-agente { background:#fef3c7; color:#92400e; }
        .badge-administrador { background:#ede9fe; color:#5b21b6; }
        .btn-sm { padding:5px 12px; border-radius:6px; font-size:12px; font-weight:600; cursor:pointer; border:none; text-decoration:none; display:inline-block; }
        .btn-editar { background:#fef3c7; color:#92400e; }
        .btn-eliminar { background:#fee2e2; color:#991b1b; }
        .btn-volver { padding:8px 18px; background:#6D28D9; color:white; border-radius:8px; text-decoration:none; font-size:13px; }
        .alerta-error { background:#fee2e2; color:#991b1b; padding:10px 14px; border-radius:8px; margin-bottom:16px; font-size:13px; }
        .vacio { text-align:center; color:#9ca3af; padding:32px; }
    </style>
</head>
<body>
    <div class="header">
        <h1>Usuarios registrados</h1>
        <a class="btn-volver" href="index.html">&larr; Volver al inicio</a>
    </div>

    <% if (error != null && !error.isEmpty()) { %>
        <div class="alerta-error">&#10060; <%= error %></div>
    <% } %>

    <table>
        <tr>
            <th>#</th>
            <th>Nombre</th>
            <th>Email</th>
            <th>Teléfono</th>
            <th>Tipo</th>
            <th>Acciones</th>
        </tr>
        <% if (usuarios == null || usuarios.isEmpty()) { %>
            <tr><td colspan="6" class="vacio">No hay usuarios registrados.</td></tr>
        <% } else { for (UsuarioDTO u : usuarios) { %>
            <tr>
                <td><%= u.getId() %></td>
                <td><%= u.getNombre() %> <%= u.getApellido() %></td>
                <td><%= u.getEmail() %></td>
                <td><%= u.getTelefono() != null ? u.getTelefono() : "-" %></td>
                <td>
                    <span class="badge badge-<%= u.getTipoUsuario() %>">
                        <%= u.getTipoUsuario() %>
                    </span>
                </td>
                <td>
                    <a class="btn-sm btn-editar" href="usuarios?accion=editar&id=<%= u.getId() %>">Editar</a>
                    <a class="btn-sm btn-eliminar"
                       href="usuarios?accion=eliminar&id=<%= u.getId() %>"
                       onclick="return confirm('¿Seguro que deseas eliminar este usuario?')">Eliminar</a>
                </td>
            </tr>
        <% } } %>
    </table>
</body>
</html>