package HelloController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helloservice.JobService;
import helloservlet.entity.JobEntity;
import helloservlet.entity.RoleEntity;

@WebServlet(name = "JobController", urlPatterns = { "/groupwork", "/groupwork-add", "/groupwork-delete",
		"/groupword-update", "/groupword-details" })
public class JobController extends HttpServlet {
	private JobService jobService = new JobService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if (path.equals("/groupwork")) {
			doGetJobTable(req, resp);
		} else if (path.equals("/groupwork-add")) {
			doGetJobAdd(req, resp);
		} else if (path.equals("/groupwork-delete")) {
			doGetJobDelete(req, resp);
		} else if (path.equals("/groupword-update")) {
			doGetEdit(req, resp);
		} else if (path.equals("/groupword-details")) {
			doGetDetailsByJobId(req, resp);

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if (path.equals("/groupwork-add")) {
			doPostJobAdd(req, resp);
		} else if (path.equals("/groupword-update")) {
			doPostJobUpdate(req, resp);
		}
	}

	private void doGetJobTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<JobEntity> list = jobService.getAllJob();
		req.setAttribute("listJob", list);
		req.getRequestDispatcher("groupwork.jsp").forward(req, resp);

	}

	private void doGetJobAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
	}

	private void doPostJobAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("job-name");
		String starDate = req.getParameter("job-starDate");
		String endDate = req.getParameter("job-endDate");

		name = setUTF8(name);

		boolean isSuccess = jobService.insert(name, starDate, endDate);
		req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);

	}

	private String setUTF8(String string) {
		byte[] bytes = string.getBytes(StandardCharsets.ISO_8859_1);
		string = new String(bytes, StandardCharsets.UTF_8);

		return string;
	}

	private void doGetJobDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		jobService.deleteJobById(id);
		req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
	}

	private void doGetEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nameEdit = req.getParameter("name"); // Lấy name
		String starDayEdit = req.getParameter("starDay");// Lấy mô tả

		String endDayEdit = req.getParameter("endDay");

		int idEdit = Integer.parseInt(req.getParameter("id"));
		System.out.println("id" + idEdit);

		req.setAttribute("nameUpdate", nameEdit);
		req.setAttribute("starDayUpdate", starDayEdit);
		req.setAttribute("endDayUpdate", endDayEdit);

		req.getRequestDispatcher("groupwork-update.jsp").forward(req, resp);

	}

	private void doPostJobUpdate(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stubs
		String name = req.getParameter("job-name");
		String starDate = req.getParameter("job-starDate");
		String endDate = req.getParameter("job-endDate");
		int id = Integer.parseInt(req.getParameter("id"));

		name = setUTF8(name);
		boolean isSucces = jobService.updateJob(name, starDate, endDate, id);

		req.getRequestDispatcher("groupwork-update.jsp").forward(req, resp);

	}

	private void doGetDetailsByJobId(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		req.getSession().setAttribute("jobDetails", jobService.getDetailsByJobId(id));
		req.getRequestDispatcher("/groupwork-details.jsp").forward(req, resp);
	}
}
