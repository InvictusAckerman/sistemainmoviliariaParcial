package com.presentacion;

import com.modelo.Usuario;
import com.servicio.UsuarioServicio;

import java.io.*;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class UsuarioServlet extends HttpServlet {

    private UsuarioServicio servicio = new UsuarioServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "formulario";

        switch (accion) {
            case "listar":   listar(request, response);        break;
            case "editar":   mostrarEditar(request, response); break;
            case "eliminar": eliminar(request, response);      break;
            case "login":    mostrarLogin(request, response);  break;
            default:         mostrarFormulario(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if ("actualizar".equals(accion))  { actualizar(request, response); return; }
        if ("login".equals(accion))       { procesarLogin(request, response); return; }
        registrar(request, response);
    }

    // ── REGISTRAR ──────────────────────────────────────────
    private void registrar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Usuario u = new Usuario();
        u.setNombre(req.getParameter("nombre"));
        u.setApellido(req.getParameter("apellido"));
        u.setEmail(req.getParameter("email"));
        u.setTelefono(req.getParameter("telefono"));
        u.setTipoUsuario(req.getParameter("tipo_usuario") != null
                ? req.getParameter("tipo_usuario") : "comprador");
        u.setContrasena(req.getParameter("contrasena"));
        try {
            servicio.registrar(u);
            resp.sendRedirect("UsuarioServlet?exito=Usuario+registrado+exitosamente");
        } catch (Exception e) {
            resp.sendRedirect("UsuarioServlet?error=" + e.getMessage());
        }
    }

    // ── LOGIN ──────────────────────────────────────────────
    private void mostrarLogin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println(htmlLogin(
            req.getParameter("error"), req.getParameter("exito")));
    }

    private void procesarLogin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String email     = req.getParameter("email");
        String contrasena = req.getParameter("contrasena");
        try {
            Usuario u = servicio.login(email, contrasena);
            // Login exitoso → redirige al index
            resp.sendRedirect("UsuarioServlet?accion=listar");
        } catch (Exception e) {
            resp.sendRedirect("UsuarioServlet?accion=login&error=" + e.getMessage());
        }
    }

    // ── LISTAR ─────────────────────────────────────────────
    private void listar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            out.println(htmlListar(servicio.obtenerTodos()));
        } catch (Exception e) {
            out.println("<p style='color:red'>Error: " + e.getMessage() + "</p>");
        }
    }

    // ── MOSTRAR FORMULARIO REGISTRO ────────────────────────
    private void mostrarFormulario(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println(htmlFormulario(
            req.getParameter("error"), req.getParameter("exito")));
    }

    // ── MOSTRAR EDITAR ─────────────────────────────────────
    private void mostrarEditar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            resp.getWriter().println(htmlEditar(servicio.obtenerPorId(id)));
        } catch (Exception e) {
            resp.sendRedirect("UsuarioServlet?accion=listar&error=" + e.getMessage());
        }
    }

    // ── ACTUALIZAR ─────────────────────────────────────────
    private void actualizar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            Usuario u = new Usuario();
            u.setId(Integer.parseInt(req.getParameter("id")));
            u.setNombre(req.getParameter("nombre"));
            u.setApellido(req.getParameter("apellido"));
            u.setEmail(req.getParameter("email"));
            u.setTelefono(req.getParameter("telefono"));
            u.setTipoUsuario(req.getParameter("tipo_usuario"));
            u.setContrasena(req.getParameter("contrasena"));
            servicio.actualizar(u);
            resp.sendRedirect("UsuarioServlet?accion=listar&exito=Usuario+actualizado");
        } catch (Exception e) {
            resp.sendRedirect("UsuarioServlet?accion=listar&error=" + e.getMessage());
        }
    }

    // ── ELIMINAR ───────────────────────────────────────────
    private void eliminar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            servicio.eliminar(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect("UsuarioServlet?accion=listar&exito=Usuario+eliminado");
        } catch (Exception e) {
            resp.sendRedirect("UsuarioServlet?accion=listar&error=" + e.getMessage());
        }
    }

    // ════════════════════════════════════════════════════════
    //  CSS compartido
    // ════════════════════════════════════════════════════════
    private String css() {
        return "<style>" +
            "* { margin:0; padding:0; box-sizing:border-box; }" +
            "body { font-family:'Segoe UI',Arial,sans-serif; min-height:100vh; display:flex; }" +
            ".panel-form { flex:0 0 42%; background:#fff; padding:48px 40px; display:flex; flex-direction:column; justify-content:center; }" +
            ".panel-deco { flex:1; position:relative; overflow:hidden; }" +
            ".panel-deco img { width:100%; height:100%; object-fit:cover; display:block; }" +
            ".panel-overlay { position:absolute; inset:0; background:rgba(30,27,75,0.45); display:flex; flex-direction:column; align-items:center; justify-content:center; padding:40px; text-align:center; }" +
            ".panel-overlay h2 { color:white; font-size:22px; margin-bottom:12px; }" +
            ".panel-overlay p { color:rgba(255,255,255,0.75); font-size:14px; line-height:1.7; max-width:240px; }" +
            ".tags { display:flex; gap:8px; margin-top:24px; flex-wrap:wrap; justify-content:center; }" +
            ".tag { background:rgba(255,255,255,0.15); color:white; font-size:12px; padding:6px 14px; border-radius:20px; }" +
            ".logo { display:flex; align-items:center; gap:8px; margin-bottom:32px; }" +
            ".logo-icon { width:32px; height:32px; background:#6D28D9; border-radius:6px; display:flex; align-items:center; justify-content:center; }" +
            ".logo span { font-size:20px; font-weight:600; color:#1a1a1a; }" +
            "h1 { font-size:24px; font-weight:600; color:#1a1a1a; margin-bottom:24px; }" +
            ".subtitulo { font-size:14px; color:#6b7280; margin-bottom:24px; }" +
            ".form-group { margin-bottom:14px; }" +
            "label { font-size:12px; color:#666; display:block; margin-bottom:4px; }" +
            "label span { color:#6D28D9; }" +
            "input, select { width:100%; padding:10px 12px; border:1px solid #d1d5db; border-radius:8px; font-size:14px; transition:border-color 0.2s; }" +
            "input:focus, select:focus { outline:none; border-color:#6D28D9; }" +
            ".btn { width:100%; padding:12px; background:#6D28D9; color:white; border:none; border-radius:8px; font-size:15px; font-weight:600; cursor:pointer; margin-top:8px; }" +
            ".btn:hover { background:#5b21b6; }" +
            ".alerta-error { background:#fee2e2; color:#991b1b; padding:10px 14px; border-radius:8px; margin-bottom:16px; font-size:13px; border:1px solid #fca5a5; }" +
            ".alerta-exito { background:#d1fae5; color:#065f46; padding:10px 14px; border-radius:8px; margin-bottom:16px; font-size:13px; border:1px solid #6ee7b7; }" +
            ".link { display:block; text-align:center; margin-top:14px; font-size:13px; color:#6D28D9; text-decoration:none; }" +
            ".page { padding:32px; background:#f9fafb; min-height:100vh; display:block; }" +
            "table { width:100%; border-collapse:collapse; background:white; border-radius:10px; overflow:hidden; box-shadow:0 1px 4px rgba(0,0,0,0.08); }" +
            "th { background:#6D28D9; color:white; padding:12px 16px; text-align:left; font-size:13px; }" +
            "td { padding:12px 16px; font-size:13px; color:#374151; border-bottom:1px solid #f3f4f6; }" +
            "tr:last-child td { border-bottom:none; }" +
            "tr:hover td { background:#faf5ff; }" +
            ".badge { padding:3px 10px; border-radius:12px; font-size:11px; font-weight:600; }" +
            ".badge-comprador { background:#dbeafe; color:#1e40af; }" +
            ".badge-vendedor { background:#d1fae5; color:#065f46; }" +
            ".badge-agente { background:#fef3c7; color:#92400e; }" +
            ".badge-administrador { background:#ede9fe; color:#5b21b6; }" +
            ".btn-sm { padding:5px 12px; border-radius:6px; font-size:12px; font-weight:600; cursor:pointer; border:none; }" +
            ".btn-editar { background:#fef3c7; color:#92400e; }" +
            ".btn-eliminar { background:#fee2e2; color:#991b1b; }" +
            ".btn-volver { display:inline-block; margin-bottom:20px; padding:8px 18px; background:#6D28D9; color:white; border-radius:8px; text-decoration:none; font-size:13px; }" +
            "</style>";
    }

    private String logoSvg() {
        return "<svg width='18' height='18' viewBox='0 0 20 20' fill='none'>" +
               "<path d='M10 2L2 9h2v9h5v-5h2v5h5V9h2L10 2z' fill='white'/></svg>";
    }

    private String panelImagen(String titulo, String desc, boolean conTags) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='panel-deco'>")
          .append("<img src='images/landscape-mountain.jpg' alt='Paisaje' ")
          .append("onerror=\"this.style.display='none';this.parentElement.style.background='linear-gradient(135deg,#1e1b4b,#4c1d95)'\">")
          .append("<div class='panel-overlay'>")
          .append("<h2>").append(titulo).append("</h2>")
          .append("<p>").append(desc).append("</p>");
        if (conTags)
            sb.append("<div class='tags'><span class='tag'>Compra</span>")
              .append("<span class='tag'>Venta</span><span class='tag'>Arriendo</span></div>");
        sb.append("</div></div>");
        return sb.toString();
    }

    // ════════════════════════════════════════════════════════
    //  HTML FORMULARIO REGISTRO
    // ════════════════════════════════════════════════════════
    private String htmlFormulario(String error, String exito) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>")
          .append("<title>Registro - Inmobiliaria</title>").append(css())
          .append("</head><body>");

        sb.append("<div class='panel-form'>")
          .append("<div class='logo'><div class='logo-icon'>").append(logoSvg()).append("</div>")
          .append("<span>Inmobiliaria</span></div>")
          .append("<h1>Crear cuenta</h1>");

        if (error != null && !error.isEmpty())
            sb.append("<div class='alerta-error'>&#10060; ").append(error).append("</div>");
        if (exito != null && !exito.isEmpty())
            sb.append("<div class='alerta-exito'>&#10003; ").append(exito).append("</div>");

        sb.append("<form action='UsuarioServlet' method='POST'>")
          .append("<div class='form-group'><label>Nombre <span>*</span></label>")
          .append("<input type='text' name='nombre' placeholder='Ej: Juan' required></div>")
          .append("<div class='form-group'><label>Apellido <span>*</span></label>")
          .append("<input type='text' name='apellido' placeholder='Ej: García' required></div>")
          .append("<div class='form-group'><label>Correo electrónico <span>*</span></label>")
          .append("<input type='email' name='email' placeholder='usuario@email.com' required></div>")
          .append("<div class='form-group'><label>Teléfono</label>")
          .append("<input type='text' name='telefono' placeholder='+57 300 000 0000'></div>")
          .append("<div class='form-group'><label>Contraseña <span>*</span></label>")
          .append("<input type='password' name='contrasena' placeholder='Mínimo 4 caracteres' required></div>")
          .append("<div class='form-group'><label>Tipo de usuario <span>*</span></label>")
          .append("<select name='tipo_usuario'>")
          .append("<option value='comprador'>Comprador</option>")
          .append("<option value='vendedor'>Vendedor</option>")
          .append("<option value='agente'>Agente</option>")
          .append("<option value='administrador'>Administrador</option>")
          .append("</select></div>")
          .append("<button class='btn' type='submit'>Registrarse</button>")
          .append("</form>")
          .append("<a class='link' href='UsuarioServlet?accion=login'>¿Ya tienes cuenta? Iniciar sesión</a>")
          .append("</div>");

        sb.append(panelImagen("Encuentra tu hogar ideal",
            "Registra tu cuenta y accede a las mejores propiedades.", true));

        sb.append("</body></html>");
        return sb.toString();
    }

    // ════════════════════════════════════════════════════════
    //  HTML LOGIN
    // ════════════════════════════════════════════════════════
    private String htmlLogin(String error, String exito) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>")
          .append("<title>Iniciar Sesión - Inmobiliaria</title>").append(css())
          .append("</head><body>");

        sb.append("<div class='panel-form'>")
          .append("<div class='logo'><div class='logo-icon'>").append(logoSvg()).append("</div>")
          .append("<span>Inmobiliaria</span></div>")
          .append("<h1>Iniciar sesión</h1>")
          .append("<p class='subtitulo'>Bienvenido de nuevo. Ingresa tus datos.</p>");

        if (error != null && !error.isEmpty())
            sb.append("<div class='alerta-error'>&#10060; ").append(error).append("</div>");
        if (exito != null && !exito.isEmpty())
            sb.append("<div class='alerta-exito'>&#10003; ").append(exito).append("</div>");

        sb.append("<form action='UsuarioServlet' method='POST'>")
          .append("<input type='hidden' name='accion' value='login'>")
          .append("<div class='form-group'><label>Correo electrónico <span>*</span></label>")
          .append("<input type='email' name='email' placeholder='usuario@email.com' required></div>")
          .append("<div class='form-group'><label>Contraseña <span>*</span></label>")
          .append("<input type='password' name='contrasena' placeholder='Tu contraseña' required></div>")
          .append("<button class='btn' type='submit'>Iniciar sesión</button>")
          .append("</form>")
          .append("<a class='link' href='UsuarioServlet'>¿No tienes cuenta? Regístrate</a>")
          .append("<a class='link' href='index.html'>&#8592; Volver al inicio</a>")
          .append("</div>");

        sb.append(panelImagen("Tu próxima propiedad te espera",
            "Accede a cientos de inmuebles disponibles en Colombia.", false));

        sb.append("</body></html>");
        return sb.toString();
    }

    // ════════════════════════════════════════════════════════
    //  HTML LISTAR
    // ════════════════════════════════════════════════════════
    private String htmlListar(List<Usuario> usuarios) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>")
          .append("<title>Usuarios - Inmobiliaria</title>").append(css())
          .append("</head><body><div class='page'>")
          .append("<a class='btn-volver' href='UsuarioServlet'>&larr; Registrar usuario</a>")
          .append("<h1 style='margin-bottom:20px;'>Usuarios registrados</h1>")
          .append("<table><tr>")
          .append("<th>#</th><th>Nombre</th><th>Email</th><th>Teléfono</th><th>Tipo</th><th>Acciones</th>")
          .append("</tr>");

        if (usuarios.isEmpty()) {
            sb.append("<tr><td colspan='6' style='text-align:center;color:#9ca3af;padding:24px;'>")
              .append("No hay usuarios registrados.</td></tr>");
        } else {
            for (Usuario u : usuarios) {
                sb.append("<tr>")
                  .append("<td>").append(u.getId()).append("</td>")
                  .append("<td>").append(u.getNombre()).append(" ").append(u.getApellido()).append("</td>")
                  .append("<td>").append(u.getEmail()).append("</td>")
                  .append("<td>").append(u.getTelefono()).append("</td>")
                  .append("<td><span class='badge badge-").append(u.getTipoUsuario()).append("'>")
                  .append(u.getTipoUsuario()).append("</span></td>")
                  .append("<td>")
                  .append("<a href='UsuarioServlet?accion=editar&id=").append(u.getId())
                  .append("'><button class='btn-sm btn-editar'>Editar</button></a> ")
                  .append("<a href='UsuarioServlet?accion=eliminar&id=").append(u.getId())
                  .append("' onclick=\"return confirm('¿Eliminar este usuario?')\">")
                  .append("<button class='btn-sm btn-eliminar'>Eliminar</button></a>")
                  .append("</td></tr>");
            }
        }
        sb.append("</table></div></body></html>");
        return sb.toString();
    }

    // ════════════════════════════════════════════════════════
    //  HTML EDITAR
    // ════════════════════════════════════════════════════════
    private String htmlEditar(Usuario u) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>")
          .append("<title>Editar Usuario - Inmobiliaria</title>").append(css())
          .append("</head><body>");

        sb.append("<div class='panel-form'>")
          .append("<div class='logo'><div class='logo-icon'>").append(logoSvg()).append("</div>")
          .append("<span>Inmobiliaria</span></div>")
          .append("<h1>Editar usuario</h1>")
          .append("<form action='UsuarioServlet' method='POST'>")
          .append("<input type='hidden' name='accion' value='actualizar'>")
          .append("<input type='hidden' name='id' value='").append(u.getId()).append("'>")
          .append("<div class='form-group'><label>Nombre <span>*</span></label>")
          .append("<input type='text' name='nombre' value='").append(u.getNombre()).append("' required></div>")
          .append("<div class='form-group'><label>Apellido <span>*</span></label>")
          .append("<input type='text' name='apellido' value='").append(u.getApellido()).append("' required></div>")
          .append("<div class='form-group'><label>Correo electrónico <span>*</span></label>")
          .append("<input type='email' name='email' value='").append(u.getEmail()).append("' required></div>")
          .append("<div class='form-group'><label>Teléfono</label>")
          .append("<input type='text' name='telefono' value='").append(u.getTelefono()).append("'></div>")
          .append("<div class='form-group'><label>Contraseña</label>")
          .append("<input type='password' name='contrasena' value='").append(u.getContrasena()).append("'></div>")
          .append("<div class='form-group'><label>Tipo de usuario</label>")
          .append("<select name='tipo_usuario'>");

        for (String tipo : new String[]{"comprador","vendedor","agente","administrador"}) {
            sb.append("<option value='").append(tipo).append("'")
              .append(tipo.equals(u.getTipoUsuario()) ? " selected" : "")
              .append(">").append(tipo).append("</option>");
        }

        sb.append("</select></div>")
          .append("<button class='btn' type='submit'>Guardar cambios</button>")
          .append("</form>")
          .append("<a class='link' href='UsuarioServlet?accion=listar'>&larr; Volver a la lista</a>")
          .append("</div>");

        sb.append(panelImagen("Actualiza tus datos",
            "Mantén tu información siempre al día.", false));

        sb.append("</body></html>");
        return sb.toString();
    }
}