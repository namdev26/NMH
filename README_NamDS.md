I.	Thiết kế biểu đồ thực thể
 
Các chức năng chính của hệ thống: Nhập trang phục từ nhà cung cấp, Thống kê dòng trang phục theo doanh thu.
Design pattern sử dụng: Decorator.
Hệ thống bao gồm các lớp thực thể:
•	Thực thể địa chỉ → Lớp Address: id, city, street.
Lớp Address dùng để lưu thông tin địa chỉ của người dùng hệ thống. 
•	Thực thể họ tên → Lớp FullName: id, firstName, lastName.
Lớp FullName dùng để lưu thông tin họ tên của người dùng hệ thống. 
•	Thực thể người dùng → Lớp User: id, address, fullname, username, password.
Lớp User dùng để lưu thông tin người dùng hệ thống bao gồm cả tài khoản đăng nhập hệ thống. 
•	Thực thể khách hàng → Lớp Customer kế thừa từ lớp User: rewardPoint, customerRanking.
Lớp Customer lưu thông tin khách hàng sử dụng hệ thống. 
•	Thực thể nhân viên → Lớp Staff kế thừa từ lớp User: salary, position.
Lớp Staff dùng để lưu thông tin nhân viên sử dụng hệ thống. 
•	Thực thể người quản lý → Lớp Manager kế thừa từ lớp Staff: managerCode, title.
Lớp Manager dùng để lưu thông tin người quản lý hệ thống, sử dụng khi nhập trang phục hoặc thống kê. 
•	Thực thể dòng trang phục → Lớp CostumeLine: id, name, description.
Lớp CostumeLine lưu thông tin các dòng trang phục trong hệ thống. 
•	Thực thể trang phục → Lớp Costume: id, name, category, size, color, material, sellPrice, stockQuantity, costumeLine.
Lớp Costume lưu thông tin các trang phục hiển thị cho khách hàng hoặc nhập từ nhà cung cấp. 
•	Thực thể hóa đơn mua trang phục → Lớp Bill: id, customer, createdTime,.
Lớp Bill lưu thông tin hóa đơn mua trang phục của khách hàng. 
•	Thực thể chi tiết hóa đơn mua trang phục → Lớp BillCostumeDetail: id, bill, costume, quantity, price.
Lớp BillCostumeDetail lưu thông tin chi tiết các trang phục xuất hiện trong một hóa đơn bán. 
•	Thực thể chi tiết trang phục được nhập → Lớp CostumeDetail: id, costume, importPrice, quantity, note, importingBill.
Thực thể CostumeDetail lưu thông tin trang phục tại thời điểm nhập, bao gồm số lượng nhập trong lần đó. 
•	Thực thể nhà cung cấp → Lớp Supplier: id, email, contact.
Lớp Supplier lưu thông tin nhà cung cấp trang phục cho hệ thống. 
•	Thực thể hóa đơn nhập trang phục → Lớp ImportingBill: id, manager, costumeDetails, supplier, importTime, totalAmount.
Lớp ImportingBill lưu thông tin hóa đơn nhập trang phục ứng với 1 người quản lý, 1 nhà cung cấp và danh sách chi tiết trang phục muốn nhập. 
•	Lớp CostumeDecorator: costume.
Lớp CostumeDecorator lưu thông tin của trang phục và sử dụng theo thiết kế design pattern Decorator. 
•	Lớp CostumeStat kế thừa từ lớp CostumeDecorator: revenue, totalQuantity.
Lớp CostumeStat lưu thông tin thống kê doanh thu, tổng số lượng bán của từng trang phục. 
•	Lớp CostumeLineDecorator: costumeLine.
Lớp CostumeLineDecorator lưu thông tin của dòng trang phục và sử dụng theo thiết kế design pattern Decorator. 
•	Lớp CostumeLineStat kế thừa từ lớp CostumeLineDecorator: revenue, totalQuantity.
Lớp CostumeLineStat lưu thông tin thống kê doanh thu, tổng số lượng bán của dòng trang phục.
II.	Thiết kế cơ sở dữ liệu
 
Cơ sở dữ liệu hệ thống được thiết kế cho từng dịch vụ riêng biệt:
1. Dịch vụ user service: Dịch vụ sử dụng để quản lý người dùng hệ thống
Danh sách các bảng của cơ sở dữ liệu ứng với dịch vụ user service:
•	tblAddress: Id (khóa chính), City, Street 
•	tblFullName: Id (khóa chính), FirstName, LastName 
•	tblUser: Id (khóa chính), Discriminator, Username, Password, AddressId (khóa ngoại tham chiếu tới bảng tblAddress), FullNameId (khóa ngoại tham chiếu tới bảng tblFullName) 
•	tblCustomer: UserId (khóa chính và là khóa ngoại tham chiếu tới tblUser), RewardPoint, CustomerRanking 
•	tblStaff: UserId (khóa chính và là khóa ngoại tham chiếu tới tblUser), Salary, Position, ManagerCode, Title 
Quan hệ giữa các bảng:
•	tblAddress - tblUser: quan hệ 1 - 1 
•	tblFullName - tblUser: quan hệ 1 - 1 
•	tblUser - tblCustomer: quan hệ 1 - 1 
•	tblUser - tblStaff: quan hệ 1 - 1 
2. Dịch vụ costume service: Dịch vụ sử dụng để quản lý thông tin liên quan tới trang phục, dòng trang phục và trang phục được nhập
Danh sách các bảng của cơ sở dữ liệu ứng với dịch vụ costume service:
•	tblCostumeLine: Id (khóa chính), Name, Description 
•	tblCostume: Id (khóa chính), Name, Category, Size, Color, Material, SellPrice, StockQuantity, CostumeLineId (khóa ngoại tham chiếu tới bảng tblCostumeLine) 
•	tblCostumeDetail: Id (khóa chính), CostumeId, ImportPrice, Quantity, Note, ImportingBillId 
•	tblBillCostumeDetail: Id (khóa chính), BillId, CostumeId (khóa ngoại tham chiếu tới bảng tblCostume), Quantity, Price 
Quan hệ giữa các bảng:
•	tblCostumeLine và tblCostume: quan hệ 1 - N 
•	tblCostume và tblCostumeDetail: quan hệ 1 - N 
•	tblCostume và tblBillCostumeDetail: quan hệ 1 - N 
3. Dịch vụ bill costume service: Dịch vụ sử dụng để quản lý thông tin hóa đơn mua trang phục và thanh toán
Danh sách các bảng của cơ sở dữ liệu ứng với dịch vụ bill costume service:
•	tblBill: Id (khóa chính), CustomerId, CreatedTime
4. Dịch vụ supplier service: Dịch vụ sử dụng để quản lý thông tin nhà cung cấp
Danh sách các bảng của cơ sở dữ liệu ứng với dịch vụ supplier service:
•	tblSupplier: Id (khóa chính), Email, Contact 
5. Dịch vụ import bill service: Dịch vụ sử dụng để quản lý thông tin hóa đơn nhập trang phục
Danh sách các bảng của cơ sở dữ liệu ứng với dịch vụ import bill service:
•	tblImportingBill: Id (khóa chính), ManagerId, SupplierId, ImportDate, TotalAmount
 
III.	Nhập trang phục từ nhà cung cấp
1. Hoạt động của module
Module cho phép người quản lý (QL) nhập trang phục: QL chọn menu quản lý → QL chọn nút Nhập trang phục → Hệ thống hiển thị form nhập trang phục gồm danh sách trang phục và danh sách nhà cung cấp → QL chọn danh sách trang phục muốn nhập điền giá nhập và số lượng, chọn 1 nhà cung cấp và ấn nhập → Hệ thống thông báo nhập trang phục thành công.
Module được thiết kế theo kiến trúc vi dịch vụ, gồm các dịch vụ: CostumeService, SupplierService, ImportingBillService.
2. Thiết kế giao diện người dùng
2.1 Giao diện trang chủ của người quản lý (managerHome.html)
 
2.2 Giao diện quản lý nhập trang phục (importingCostume.html)
 
2.3 Giao diện quản lý nhập trang phục thành công (importingSuccess.html)
 
3. Thiết kế biểu đồ lớp chi tiết
 
Phía Client
Phía Client
Tầng View:
Các lớp giao diện gồm có: managerHome.html, importingCostume.html, importingSuccess.html
Tầng Controller: Lớp điều khiển APIGateway
•	getImportingCostumeForm(): Thực hiện gửi yêu cầu tới service CostumeService nhận về danh sách các trang phục, gửi yêu cầu tới SupplierService nhận về danh sách nhà cung cấp, và trả về giao diện importingCostume.html. 
•	handleImportBill(): Nhận danh sách chi tiết trang phục muốn nhập (costumeId, importPrice, quantity, note), supplierId là nhà cung cấp mà người quản lý chọn, và managerId lấy từ thông tin người quản lý đang đăng nhập. Gửi toàn bộ thông tin tới service ImportingBillService để tạo hóa đơn nhập và lưu chi tiết nhập. Nếu nhận được phản hồi thành công từ ImportingBillService, trả về giao diện importingSuccess.html. Nếu nhận được phản hồi lỗi, hiển thị thông báo lỗi trên giao diện importingCostume.html. 
________________________________________
Phía Server
Thực hiện thiết kế theo kiến trúc vi dịch vụ, gồm các dịch vụ sau:
1. Service CostumeService
Thực hiện các yêu cầu về Costume, CostumeDetail.
Lớp điều khiển: CostumeController
•	getCostumeListToImport(): Nhận request từ APIGateway, ủy thác xử lý cho lớp CostumeService. 
•	getCostumeListByIds(): Nhận request kèm danh sách costumeId, ủy thác xử lý cho lớp CostumeService. 
•	createImportCostumeDetail(): Nhận request kèm danh sách CostumeDetail và importingBillId, ủy thác xử lý cho lớp CostumeService. 
Lớp nghiệp vụ: CostumeService
•	getCostumeListToImport(): Trả về danh sách các trang phục để tiến hành nhập. 
•	getCostumeListByIds(): Nhận vào danh sách costumeId, trả về danh sách trang phục Costume tương ứng. 
•	createImportCostumeDetail(): Nhận danh sách CostumeDetail (costumeId, importPrice, quantity, note) và importingBillId, điều khiển CostumeDetailRepository thêm các bản ghi CostumeDetail vào CSDL và cập nhật StockQuantity tương ứng trên bảng tblCostume. Trả về danh sách CostumeDetail vừa tạo. 
2. Service SupplierService
Thực hiện các yêu cầu liên quan tới nhà cung cấp.
Lớp điều khiển: SupplierController
•	getSupplierList(): Nhận request từ APIGateway, ủy thác xử lý cho lớp SupplierService. 
•	getSupplierById(): Nhận request kèm supplierId, ủy thác xử lý cho lớp SupplierService. 
Lớp nghiệp vụ: SupplierService
•	getSupplierList(): Trả về danh sách các nhà cung cấp trong CSDL của service. 
•	getSupplierById(): Nhận vào supplierId, trả về đối tượng nhà cung cấp Supplier tương ứng. 
3. Service ImportingBillService
Thực hiện các yêu cầu liên quan tới phiếu nhập.
Lớp điều khiển: ImportingBillController
•	createImportingBill(): Nhận request từ APIGateway, ủy thác xử lý cho lớp ImportingBillService. 
Lớp nghiệp vụ: ImportingBillService
•	createImportingBill(): Nhận thông tin gồm supplierId, managerId và danh sách chi tiết nhập (costumeId, importPrice, quantity, note). Kiểm tra tính hợp lệ dữ liệu đầu vào, nếu không hợp lệ trả về lỗi ngay. Gọi SupplierController để xác minh nhà cung cấp tồn tại, gọi CostumeController để xác minh danh sách trang phục tồn tại. Tạo và lưu phiếu nhập ImportingBill vào CSDL. Sau đó gọi CostumeController để tạo danh sách CostumeDetail tương ứng với importingBillId vừa tạo và cập nhật StockQuantity của từng trang phục. Nếu bước gọi CostumeController thất bại, thực hiện xóa ImportingBill vừa tạo (compensating transaction) và trả về lỗi. Trả về ImportingBill đã tạo thành công. 
4. Thiết kế biểu đồ tuần tự
 
 
Sequence riêng: CostumeService_1
1.1. Chức năng lấy danh sách trang phục để nhập
Luồng 1: Lấy danh sách trang phục để nhập
1.	APIGateway gửi yêu cầu tới lớp CostumeController của service CostumeService. 
2.	Lớp CostumeController thực hiện phương thức getCostumeListToImport(). 
3.	Hàm getCostumeListToImport() của CostumeController gọi lớp CostumeService. 
4.	Lớp CostumeService thực hiện phương thức getCostumeListToImport(). 
5.	Hàm getCostumeListToImport() của CostumeService gọi lớp CostumeRepository. 
6.	Lớp CostumeRepository thực hiện phương thức findCostumeListToImport(). 
7.	Hàm findCostumeListToImport() gọi lớp Costume yêu cầu đóng gói dữ liệu. 
8.	Lớp Costume thực hiện đóng gói đối tượng. 
9.	Lớp Costume trả về đối tượng cho hàm findCostumeListToImport(). 
10.	Hàm findCostumeListToImport() trả về danh sách trang phục cho hàm getCostumeListToImport() của CostumeService. 
11.	Hàm getCostumeListToImport() của CostumeService trả về danh sách trang phục cho hàm getCostumeListToImport() của CostumeController. 
12.	Hàm getCostumeListToImport() của CostumeController gửi phản hồi về cho APIGateway. 
Luồng 2: Lấy danh sách trang phục theo danh sách id
13.	ImportingBillController gửi yêu cầu tới lớp CostumeController của service CostumeService. 
14.	Lớp CostumeController thực hiện phương thức getCostumeListByIds(). 
15.	Hàm getCostumeListByIds() của CostumeController gọi lớp CostumeService. 
16.	Lớp CostumeService thực hiện phương thức getCostumeListByIds(). 
17.	Hàm getCostumeListByIds() của CostumeService gọi lớp CostumeRepository. 
18.	Lớp CostumeRepository thực hiện phương thức findCostumeListByIds(). 
19.	Hàm findCostumeListByIds() gọi lớp Costume yêu cầu đóng gói dữ liệu. 
20.	Lớp Costume thực hiện đóng gói đối tượng. 
21.	Lớp Costume trả về danh sách đối tượng cho hàm findCostumeListByIds(). 
22.	Hàm findCostumeListByIds() trả về danh sách trang phục cho hàm getCostumeListByIds() của CostumeService. 
23.	Hàm getCostumeListByIds() của CostumeService trả về danh sách trang phục cho hàm getCostumeListByIds() của CostumeController. 
24.	Hàm getCostumeListByIds() của CostumeController gửi phản hồi về cho ImportingBillController.
Sequence riêng: SupplierService
Luồng 1: Lấy danh sách nhà cung cấp
1.	APIGateway gửi yêu cầu tới lớp SupplierController của service SupplierService. 
2.	Lớp SupplierController thực hiện phương thức getSupplierList(). 
3.	Hàm getSupplierList() của SupplierController gọi lớp SupplierService. 
4.	Lớp SupplierService thực hiện phương thức getSupplierList(). 
5.	Hàm getSupplierList() của SupplierService gọi lớp SupplierRepository. 
6.	Lớp SupplierRepository thực hiện phương thức findSupplierList(). 
7.	Hàm findSupplierList() gọi lớp Supplier yêu cầu đóng gói dữ liệu. 
8.	Lớp Supplier thực hiện đóng gói đối tượng. 
9.	Lớp Supplier trả về đối tượng cho hàm findSupplierList(). 
10.	Hàm findSupplierList() trả về danh sách nhà cung cấp cho hàm getSupplierList() của SupplierService. 
11.	Hàm getSupplierList() của SupplierService trả về danh sách nhà cung cấp cho hàm getSupplierList() của SupplierController. 
12.	Hàm getSupplierList() của SupplierController gửi phản hồi về cho APIGateway. 
Luồng 2: Lấy nhà cung cấp theo id
13.	ImportingBillController gửi yêu cầu tới lớp SupplierController của service SupplierService. 
14.	Lớp SupplierController thực hiện phương thức getSupplierById(). 
15.	Hàm getSupplierById() của SupplierController gọi lớp SupplierService. 
16.	Lớp SupplierService thực hiện phương thức getSupplierById(). 
17.	Hàm getSupplierById() của SupplierService gọi lớp SupplierRepository. 
18.	Lớp SupplierRepository thực hiện phương thức findSupplierById(). 
19.	Hàm findSupplierById() gọi lớp Supplier yêu cầu đóng gói dữ liệu. 
20.	Lớp Supplier thực hiện đóng gói đối tượng. 
21.	Lớp Supplier trả về đối tượng cho hàm findSupplierById(). 
22.	Hàm findSupplierById() trả về đối tượng nhà cung cấp cho hàm getSupplierById() của SupplierService. 
23.	Hàm getSupplierById() của SupplierService trả về đối tượng nhà cung cấp cho hàm getSupplierById() của SupplierController. 
24.	Hàm getSupplierById() của SupplierController gửi phản hồi về cho ImportingBillController.
Sequence riêng: ImportingBillService
Luồng 1: Tạo phiếu nhập thành công
1.	APIGateway gửi yêu cầu tới lớp ImportingBillController của service ImportingBillService. 
2.	Lớp ImportingBillController thực hiện phương thức createImportingBill(). 
3.	Hàm createImportingBill() của ImportingBillController gọi lớp ImportingBillService. 
4.	Lớp ImportingBillService thực hiện phương thức createImportingBill(). 
5.	Hàm createImportingBill() nhận request gồm supplierId, managerId, danh sách chi tiết nhập (costumeId, importPrice, quantity, note). 
6.	Hàm createImportingBill() kiểm tra tính hợp lệ của dữ liệu đầu vào. Nếu không hợp lệ, trả về lỗi ngay cho ImportingBillController và APIGateway (kết thúc luồng). 
7.	Hàm createImportingBill() của ImportingBillService gửi yêu cầu tới lớp SupplierController của service SupplierService để xác minh nhà cung cấp theo supplierId. 
8.	Lớp SupplierController thực hiện phương thức getSupplierById(). 
9.	Hàm getSupplierById() của SupplierController gọi lớp SupplierService. 
10.	Lớp SupplierService thực hiện phương thức getSupplierById(). 
11.	Hàm getSupplierById() của SupplierService gọi lớp SupplierRepository. 
12.	Lớp SupplierRepository thực hiện phương thức findSupplierById(). 
13.	Hàm findSupplierById() gọi lớp Supplier yêu cầu đóng gói dữ liệu. 
14.	Lớp Supplier thực hiện đóng gói đối tượng. 
15.	Lớp Supplier trả về đối tượng cho hàm findSupplierById(). 
16.	Hàm findSupplierById() trả về đối tượng nhà cung cấp cho hàm getSupplierById() của SupplierService. 
17.	Hàm getSupplierById() của SupplierService trả về đối tượng nhà cung cấp cho hàm getSupplierById() của SupplierController. 
18.	Hàm getSupplierById() của SupplierController gửi phản hồi về cho hàm createImportingBill() của ImportingBillService. Nếu nhà cung cấp không tồn tại, trả về lỗi cho ImportingBillController và APIGateway (kết thúc luồng). 
19.	Hàm createImportingBill() của ImportingBillService gửi yêu cầu tới lớp CostumeController của service CostumeService để xác minh danh sách trang phục theo danh sách costumeId. 
20.	Lớp CostumeController thực hiện phương thức getCostumeListByIds(). 
21.	Hàm getCostumeListByIds() của CostumeController gọi lớp CostumeService. 
22.	Lớp CostumeService thực hiện phương thức getCostumeListByIds(). 
23.	Hàm getCostumeListByIds() của CostumeService gọi lớp CostumeRepository. 
24.	Lớp CostumeRepository thực hiện phương thức findCostumeListByIds(). 
25.	Hàm findCostumeListByIds() gọi lớp Costume yêu cầu đóng gói dữ liệu. 
26.	Lớp Costume thực hiện đóng gói đối tượng. 
27.	Lớp Costume trả về danh sách đối tượng cho hàm findCostumeListByIds(). 
28.	Hàm findCostumeListByIds() trả về danh sách trang phục cho hàm getCostumeListByIds() của CostumeService. 
29.	Hàm getCostumeListByIds() của CostumeService trả về danh sách trang phục cho hàm getCostumeListByIds() của CostumeController. 
30.	Hàm getCostumeListByIds() của CostumeController gửi phản hồi về cho hàm createImportingBill() của ImportingBillService. Nếu có costumeId không tồn tại, trả về lỗi cho ImportingBillController và APIGateway (kết thúc luồng). 
31.	Hàm createImportingBill() của ImportingBillService gọi lớp ImportingBill yêu cầu đóng gói dữ liệu phiếu nhập. 
32.	Lớp ImportingBill thực hiện đóng gói đối tượng phiếu nhập. 
33.	Lớp ImportingBill trả về đối tượng cho hàm createImportingBill() của ImportingBillService. 
34.	Hàm createImportingBill() của ImportingBillService gọi lớp ImportingBillRepository. 
35.	Lớp ImportingBillRepository thực hiện phương thức saveImportingBill(). 
36.	Hàm saveImportingBill() gọi lớp ImportingBill yêu cầu đóng gói dữ liệu để lưu. 
37.	Lớp ImportingBill thực hiện đóng gói đối tượng. 
38.	Lớp ImportingBill trả về đối tượng cho hàm saveImportingBill(). 
39.	Hàm saveImportingBill() trả về ImportingBill đã lưu cho hàm createImportingBill() của ImportingBillService. 
40.	Hàm createImportingBill() của ImportingBillService gửi yêu cầu tới lớp CostumeController của service CostumeService để tạo danh sách CostumeDetail và cập nhật StockQuantity, kèm theo importingBillId vừa tạo. 
41.	Lớp CostumeController thực hiện phương thức createImportCostumeDetail(). 
42.	Hàm createImportCostumeDetail() của CostumeController gọi lớp CostumeService. 
43.	Lớp CostumeService thực hiện phương thức createImportCostumeDetail(). 
44.	Hàm createImportCostumeDetail() của CostumeService gọi lớp CostumeDetailRepository. 
45.	Lớp CostumeDetailRepository thực hiện phương thức saveAllCostumeDetail() để lưu danh sách CostumeDetail và cập nhật StockQuantity trên tblCostume. 
46.	Lớp CostumeDetailRepository trả về kết quả cho hàm createImportCostumeDetail() của CostumeService. 
47.	Hàm createImportCostumeDetail() của CostumeService trả về kết quả cho hàm createImportCostumeDetail() của CostumeController. 
48.	Hàm createImportCostumeDetail() của CostumeController gửi phản hồi về cho hàm createImportingBill() của ImportingBillService. 
49.	Hàm createImportingBill() của ImportingBillService trả về kết quả thành công cho hàm createImportingBill() của ImportingBillController. 
50.	Hàm createImportingBill() của ImportingBillController gửi phản hồi thành công về cho APIGateway.
Luồng 2: Tạo phiếu nhập thất bại (compensating transaction)
1.	Các bước 1–39 thực hiện tương tự Luồng 1. 
2.	Bước 40: Hàm createImportingBill() của ImportingBillService gửi yêu cầu tới CostumeController để tạo CostumeDetail nhưng nhận phản hồi lỗi. 
3.	Hàm createImportingBill() của ImportingBillService gọi lớp ImportingBillRepository thực hiện phương thức deleteImportingBill() để xóa phiếu nhập vừa lưu (compensating transaction). 
4.	Hàm createImportingBill() của ImportingBillService trả về lỗi cho hàm createImportingBill() của ImportingBillController. 
5.	Hàm createImportingBill() của ImportingBillController gửi phản hồi lỗi về cho APIGateway.


IV.	Module Thống kê dòng trang phục theo doanh thu
1.  Hoạt động của module
Cho phép quản lý thống kê doanh thu bán trang phục theo dòng trang phục với mô tả chi tiết nghiệp vụ: QL chọn menu thống kê → chọn thống kê doanh thu theo dòng trang phục → nhập thời gian bắt đầu và kết thúc thống kê → danh sách các dòng trang phục hiện ra, mỗi dòng cho 1 dòng trang phục: Mã dòng trang phục, tên dòng trang phục, tổng số lượng sản phẩm bán ra, tổng doanh thu thu được, sắp xếp theo chiều giảm dần của tổng doanh thu.
QL click vào một dòng trang phục thì hiện lên chi tiết tổng số tiền thu được của từng trang phục thuộc dòng đó, mỗi dòng tương ứng: mã trang phục, tên trang phục, số lượng bán ra, tổng tiền thu được. Sắp xếp theo chiều giảm dần của tổng tiền thu được hoặc theo tên trang phục.
Click vào một trang phục thì hiện lên bảng danh sách các hóa đơn đã bán của trang phục đó, mỗi dòng tương ứng 1 hóa đơn: tên KH nếu có, số lượng mua, tổng tiền, thời gian thanh toán, sắp xếp theo thời gian thanh toán.
Phân tích Design Pattern:
Thiết kế sử dụng design pattern Decorator đối với chức năng thống kê dòng trang phục theo doanh thu và thống kê trang phục theo doanh thu.
Ưu điểm:
Mở rộng tính linh hoạt cho lớp CostumeLine hoặc Costume mà không cần sửa đổi lớp gốc. 
Nếu cần thêm hay loại bỏ 1 chức năng thống kê, chỉ cần tạo mới hoặc xóa decorator tương ứng mà không cần sửa đổi lớp gốc hay ảnh hưởng đến các tính năng khác. 
Tăng khả năng tái sử dụng, ví dụ hệ thống cần bổ sung chức năng thống kê dòng trang phục theo ngày tháng, có thể tạo 1 lớp thống kê mới và kế thừa từ lớp CostumeLineDecorator có sẵn. 
Giảm sự phụ thuộc giữa các lớp thống kê, nếu có nhiều chức năng thống kê dòng trang phục (thống kê dòng trang phục theo doanh thu, thống kê dòng trang phục theo mùa, …), các lớp này sẽ không ảnh hưởng đến cấu trúc của lớp khác.
2. Thiết kế giao diện người dùng
2.1 Giao diện trang chủ của người quản lý (managerHome.html)
 
2.2 Giao diện biểu mẫu thống kê dòng trang phục (costumeLineStatForm.html)
 
2.3 Giao diện thống kê dòng trang phục (costumeLineStat.html)
 
2.4 Giao diện thống kê trang phục (costumeStat.html)
 
2. 5 Giao diện kết quả hoá đơn (billResult.html)
 
3. Thiết kế biểu đồ lớp chi tiết
Phía Client
Tầng View:
Các lớp giao diện gồm có: managerHome.html, costumeLineStatForm.html, costumeLineStat.html, costumeStat.html, billResult.html
Tầng Controller: Lớp điều khiển APIGateway
•	getCostumeLineStatForm(): Thực hiện trả về giao diện costumeLineStatForm.html. 
•	getCostumeLineStat(): Nhận vào startDate và endDate. Gửi yêu cầu tới BillCostumeService để lấy danh sách billId trong khoảng thời gian đó. Gửi yêu cầu tới CostumeService với danh sách billId để lấy danh sách BillCostumeDetail kèm thông tin Costume và CostumeLine. Áp dụng Decorator pattern tính tổng doanh thu, tổng số lượng bán theo từng dòng trang phục, tạo danh sách CostumeLineStat. Trả về giao diện costumeLineStat.html kèm danh sách CostumeLineStat và truyền startDate, endDate qua model. 
•	getCostumeStat(): Nhận vào costumeLineId, startDate và endDate. Gửi yêu cầu tới BillCostumeService để lấy danh sách billId trong khoảng thời gian đó. Gửi yêu cầu tới CostumeService với costumeLineId và danh sách billId để lấy danh sách BillCostumeDetail của dòng trang phục đó. Áp dụng Decorator pattern tính tổng doanh thu, tổng số lượng bán theo từng trang phục, tạo danh sách CostumeStat. Trả về giao diện costumeStat.html kèm danh sách CostumeStat và truyền costumeLineId, startDate, endDate qua model. 
•	getBillListByCostume(): Nhận vào costumeId, startDate và endDate. Gửi yêu cầu tới BillCostumeService để lấy danh sách billId trong khoảng thời gian đó. Gửi yêu cầu tới CostumeService với costumeId và danh sách billId để lấy danh sách BillCostumeDetail của trang phục đó. Gửi yêu cầu tới BillCostumeService với danh sách billId để lấy thông tin chi tiết hóa đơn. Gửi yêu cầu tới UserService với danh sách customerId để lấy tên khách hàng nếu cần hiển thị. Trả về giao diện billResult.html kèm danh sách Bill đã được gắn thông tin. 
________________________________________
Phía Server
Thực hiện thiết kế theo kiến trúc vi dịch vụ, gồm các dịch vụ sau:
1. Service CostumeService
Thực hiện các yêu cầu liên quan đến CostumeLine, Costume, BillCostumeDetail.
Lớp điều khiển: CostumeController
•	getBillCostumeDetailListByBillIds(): Nhận request kèm danh sách billId, ủy thác xử lý cho lớp CostumeService. 
•	getBillCostumeDetailListByCostumeLineAndBillIds(): Nhận request kèm costumeLineId và billId[], ủy thác xử lý cho lớp CostumeService. 
•	getBillCostumeDetailListByCostumeAndBillIds(): Nhận request kèm costumeId và billId[], ủy thác xử lý cho lớp CostumeService. 
Lớp nghiệp vụ: CostumeService
•	getBillCostumeDetailListByBillIds(): Nhận vào danh sách billId, trả về danh sách BillCostumeDetail có billId nằm trong danh sách đó (bao gồm thông tin Costume và CostumeLine tương ứng). 
•	getBillCostumeDetailListByCostumeLineAndBillIds(): Nhận vào costumeLineId và danh sách billId, trả về danh sách BillCostumeDetail thuộc dòng trang phục đó và có billId nằm trong danh sách. 
•	getBillCostumeDetailListByCostumeAndBillIds(): Nhận vào costumeId và danh sách billId, trả về danh sách BillCostumeDetail của trang phục đó và có billId nằm trong danh sách. 
2. Service BillCostumeService
Thực hiện các yêu cầu liên quan đến hóa đơn bán trang phục.
Lớp điều khiển: BillCostumeController
•	getBillIdsByDateRange(): Nhận request kèm startDate, endDate, ủy thác xử lý cho lớp BillCostumeService. 
•	getBillListByIds(): Nhận request kèm danh sách billId, ủy thác xử lý cho lớp BillCostumeService. 
Lớp nghiệp vụ: BillCostumeService
•	getBillIdsByDateRange(): Nhận vào thời gian bắt đầu và thời gian kết thúc, trả về danh sách id của các hóa đơn được tạo ra trong khoảng thời gian trên. 
•	getBillListByIds(): Nhận vào danh sách billId, trả về danh sách hóa đơn Bill có id nằm trong danh sách đó. 
3. Service UserService
Thực hiện các yêu cầu liên quan đến thông tin người dùng.
Lớp điều khiển: UserController
•	getUserListByIds(): Nhận request kèm danh sách user id, ủy thác xử lý cho lớp UserService. 
Lớp nghiệp vụ: UserService
•	getUserListByIds(): Nhận vào danh sách user id, thực hiện trả về danh sách người dùng User có id nằm trong danh sách trên.
4. Thiết kế biểu đồ tuần tự
 

Sequence riêng: CostumeService
Luồng 1: Lấy danh sách BillCostumeDetail theo danh sách billId
1.	APIGateway gửi yêu cầu (kèm danh sách billId) tới lớp CostumeController của service CostumeService. 
2.	Lớp CostumeController thực hiện phương thức getBillCostumeDetailListByBillIds(). 
3.	Hàm getBillCostumeDetailListByBillIds() của CostumeController gọi lớp CostumeService. 
4.	Lớp CostumeService thực hiện phương thức getBillCostumeDetailListByBillIds(). 
5.	Hàm getBillCostumeDetailListByBillIds() của CostumeService gọi lớp BillCostumeDetailRepository. 
6.	Lớp BillCostumeDetailRepository thực hiện phương thức findByBillIds(). 
7.	Hàm findByBillIds() gọi lớp BillCostumeDetail yêu cầu đóng gói dữ liệu (bao gồm thông tin Costume và CostumeLine). 
8.	Lớp BillCostumeDetail thực hiện đóng gói đối tượng. 
9.	Lớp BillCostumeDetail trả về đối tượng cho hàm findByBillIds(). 
10.	Hàm findByBillIds() của BillCostumeDetailRepository trả về danh sách BillCostumeDetail cho hàm getBillCostumeDetailListByBillIds() của CostumeService. 
11.	Hàm getBillCostumeDetailListByBillIds() của CostumeService trả về danh sách BillCostumeDetail cho hàm getBillCostumeDetailListByBillIds() của CostumeController. 
12.	Hàm getBillCostumeDetailListByBillIds() của CostumeController gửi phản hồi về cho APIGateway. 
Luồng 2: Lấy danh sách BillCostumeDetail theo dòng trang phục và danh sách billId
13.	APIGateway gửi yêu cầu (kèm costumeLineId và danh sách billId) tới lớp CostumeController của service CostumeService. 
14.	Lớp CostumeController thực hiện phương thức getBillCostumeDetailListByCostumeLineAndBillIds(). 
15.	Hàm getBillCostumeDetailListByCostumeLineAndBillIds() của CostumeController gọi lớp CostumeService. 
16.	Lớp CostumeService thực hiện phương thức getBillCostumeDetailListByCostumeLineAndBillIds(). 
17.	Hàm getBillCostumeDetailListByCostumeLineAndBillIds() của CostumeService gọi lớp BillCostumeDetailRepository. 
18.	Lớp BillCostumeDetailRepository thực hiện phương thức findByCostumeLineAndBillIds(). 
19.	Hàm findByCostumeLineAndBillIds() gọi lớp BillCostumeDetail yêu cầu đóng gói dữ liệu. 
20.	Lớp BillCostumeDetail thực hiện đóng gói đối tượng. 
21.	Lớp BillCostumeDetail trả về danh sách đối tượng cho hàm findByCostumeLineAndBillIds(). 
22.	Hàm findByCostumeLineAndBillIds() của BillCostumeDetailRepository trả về danh sách BillCostumeDetail cho hàm getBillCostumeDetailListByCostumeLineAndBillIds() của CostumeService. 
23.	Hàm getBillCostumeDetailListByCostumeLineAndBillIds() của CostumeService trả về danh sách BillCostumeDetail cho hàm getBillCostumeDetailListByCostumeLineAndBillIds() của CostumeController. 
24.	Hàm getBillCostumeDetailListByCostumeLineAndBillIds() của CostumeController gửi phản hồi về cho APIGateway. 
Luồng 3: Lấy danh sách BillCostumeDetail theo trang phục và danh sách billId
25.	APIGateway gửi yêu cầu (kèm costumeId và danh sách billId) tới lớp CostumeController của service CostumeService. 
26.	Lớp CostumeController thực hiện phương thức getBillCostumeDetailListByCostumeAndBillIds(). 
27.	Hàm getBillCostumeDetailListByCostumeAndBillIds() của CostumeController gọi lớp CostumeService. 
28.	Lớp CostumeService thực hiện phương thức getBillCostumeDetailListByCostumeAndBillIds(). 
29.	Hàm getBillCostumeDetailListByCostumeAndBillIds() của CostumeService gọi lớp BillCostumeDetailRepository. 
30.	Lớp BillCostumeDetailRepository thực hiện phương thức findByCostumeAndBillIds(). 
31.	Hàm findByCostumeAndBillIds() gọi lớp BillCostumeDetail yêu cầu đóng gói dữ liệu. 
32.	Lớp BillCostumeDetail thực hiện đóng gói đối tượng. 
33.	Lớp BillCostumeDetail trả về danh sách đối tượng cho hàm findByCostumeAndBillIds(). 
34.	Hàm findByCostumeAndBillIds() của BillCostumeDetailRepository trả về danh sách BillCostumeDetail cho hàm getBillCostumeDetailListByCostumeAndBillIds() của CostumeService. 
35.	Hàm getBillCostumeDetailListByCostumeAndBillIds() của CostumeService trả về danh sách BillCostumeDetail cho hàm getBillCostumeDetailListByCostumeAndBillIds() của CostumeController. 
36.	Hàm getBillCostumeDetailListByCostumeAndBillIds() của CostumeController gửi phản hồi về cho APIGateway. 
________________________________________
Sequence riêng: BillCostumeService
Luồng 1: Lấy danh sách billId theo khoảng thời gian
1.	APIGateway gửi yêu cầu (kèm startDate, endDate) tới lớp BillCostumeController của service BillCostumeService. 
2.	Lớp BillCostumeController thực hiện phương thức getBillIdsByDateRange(). 
3.	Hàm getBillIdsByDateRange() của BillCostumeController gọi lớp BillCostumeService. 
4.	Lớp BillCostumeService thực hiện phương thức getBillIdsByDateRange(). 
5.	Hàm getBillIdsByDateRange() của BillCostumeService gọi lớp BillRepository. 
6.	Lớp BillRepository thực hiện phương thức findIdsByDateRange(). 
7.	Hàm findIdsByDateRange() truy vấn CSDL lọc theo khoảng thời gian và trả về danh sách id. 
8.	Hàm findIdsByDateRange() của BillRepository trả về danh sách billId cho hàm getBillIdsByDateRange() của BillCostumeService. 
9.	Hàm getBillIdsByDateRange() của BillCostumeService trả về danh sách billId cho hàm getBillIdsByDateRange() của BillCostumeController. 
10.	Hàm getBillIdsByDateRange() của BillCostumeController gửi phản hồi danh sách billId về cho APIGateway. 
Luồng 2: Lấy danh sách hóa đơn theo danh sách billId
11.	APIGateway gửi yêu cầu (kèm danh sách billId) tới lớp BillCostumeController của service BillCostumeService. 
12.	Lớp BillCostumeController thực hiện phương thức getBillListByIds(). 
13.	Hàm getBillListByIds() của BillCostumeController gọi lớp BillCostumeService. 
14.	Lớp BillCostumeService thực hiện phương thức getBillListByIds(). 
15.	Hàm getBillListByIds() của BillCostumeService gọi lớp BillRepository. 
16.	Lớp BillRepository thực hiện phương thức findByIds(). 
17.	Hàm findByIds() gọi lớp Bill yêu cầu đóng gói dữ liệu. 
18.	Lớp Bill thực hiện đóng gói đối tượng. 
19.	Lớp Bill trả về danh sách đối tượng cho hàm findByIds(). 
20.	Hàm findByIds() của BillRepository trả về danh sách hóa đơn cho hàm getBillListByIds() của BillCostumeService. 
21.	Hàm getBillListByIds() của BillCostumeService trả về danh sách hóa đơn cho hàm getBillListByIds() của BillCostumeController. 
22.	Hàm getBillListByIds() của BillCostumeController gửi phản hồi về cho APIGateway. 
________________________________________
Sequence riêng: UserService
Luồng 1: Lấy danh sách người dùng theo danh sách id
1.	APIGateway gửi yêu cầu (kèm danh sách customerId) tới lớp UserController của service UserService. 
2.	Lớp UserController thực hiện phương thức getUserListByIds(). 
3.	Hàm getUserListByIds() của UserController gọi lớp UserService. 
4.	Lớp UserService thực hiện phương thức getUserListByIds(). 
5.	Hàm getUserListByIds() của UserService gọi lớp UserRepository. 
6.	Lớp UserRepository thực hiện phương thức findByIds(). 
7.	Hàm findByIds() gọi lớp User yêu cầu đóng gói dữ liệu. 
8.	Lớp User thực hiện đóng gói đối tượng. 
9.	Lớp User trả về danh sách đối tượng cho hàm findByIds(). 
10.	Hàm findByIds() của UserRepository trả về danh sách người dùng cho hàm getUserListByIds() của UserService. 
11.	Hàm getUserListByIds() của UserService trả về danh sách người dùng cho hàm getUserListByIds() của UserController. 
12.	Hàm getUserListByIds() của UserController gửi phản hồi về cho APIGateway.

