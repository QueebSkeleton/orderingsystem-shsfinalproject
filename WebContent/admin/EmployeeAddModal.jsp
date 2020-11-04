<div class="modal fade" id="addEmployeeModal">
	<div class="modal-dialog">
		<div class="modal-content">
		
			<div class="modal-header">
				<h1 class="modal-title">Add Employee</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<form class="form" method="POST" action="AddEmployee.do" enctype="multipart/form-data" autocomplete="off">
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label">Image:</label>
						<input type="file" name="image" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">First Name:</label>
						<input type="text" name="firstName" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Middle Name:</label>
						<input type="text" name="middleName" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Last Name:</label>
						<input type="text" name="lastName" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Birthdate:</label>
						<input type="date" name="birthdate" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Address:</label>
						<input type="text" name="address" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Contact Number:</label>
						<input type="number" name="contactNumber" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Email Address:</label>
						<input type="email" name="emailAddress" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Username:</label>
						<input type="text" name="username" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Password:</label>
						<input type="text" name="password" class="form-control" required/>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">Add</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
				</div>
			</form>
			
		</div>
	</div>
</div>