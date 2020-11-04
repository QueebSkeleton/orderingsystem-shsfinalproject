<!-- Bootstrap core JavaScript-->
<script src="../assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="../assets/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Page level plugin JavaScript-->
<script src="../assets/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="../assets/vendor/datatables/js/dataTables.bootstrap4.js"></script>
<script src="../assets/vendor/datatables/js/dataTables.buttons.min.js"></script>
<script src="../assets/vendor/datatables/js/buttons.flash.min.js"></script>
<script src="../assets/vendor/datatables/js/jszip.min.js"></script>
<script src="../assets/vendor/datatables/js/pdfmake.min.js"></script>
<script src="../assets/vendor/datatables/js/vfs_fonts.js"></script>
<script src="../assets/vendor/datatables/js/buttons.bootstrap4.js"></script>
<script src="../assets/vendor/datatables/js/buttons.print.js"></script>
    
<!-- Custom scripts for all pages-->
<script src="../assets/js/sb-admin.min.js"></script>

<script>
	$(document).ready(function() {
	    $('#dataTable').DataTable( {
	        dom: "<'row'<'col-sm-12 col-md-6'l><'col-sm-12 col-md-3 text-right'B><'col-sm-12 col-md-3'f>>" +
	        	"<'row'<'col-sm-12 col-md-12't>>" +
	        	"<'row'<'col-sm-12 col-md-6'i><'col-sm-12 col-md-6'p>>",
	        buttons: [
	        	'pdf', 'print', 'csv'
	        ]
	    } );
	} );
</script>